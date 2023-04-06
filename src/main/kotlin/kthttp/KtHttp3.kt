package kthttp

import com.google.gson.Gson
import kotlinx.coroutines.suspendCancellableCoroutine
import kthttp.anno.Query
import kthttp.anno.Get
import kthttp.anno.Path
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Proxy
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

interface ApiService3 {
    @Get("article/list/{page_num}/json")
    fun listArticleSync(
        @Path("page_num") pageNum: Int,
        @Query("author") author: String,
        @Query("page_size") pageSize: Int
    ): PageResult

    @Get("article/list/{page_num}/json")
    fun listArticleAsync(
        @Path("page_num") pageNum: Int,
        @Query("author") author: String,
        @Query("page_size") pageSize: Int
    ): KtCall<PageResult>
}

interface CallBack<T : Any> {
    fun onSuccess(data: T)
    fun onFail(throwable: Throwable)
}

class KtCall<T : Any>(
    private val call: Call,
    private val gson: Gson,
    private val type: Type
) {
    fun call(callBack: CallBack<T>): Call {
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callBack.onFail(e)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val t = gson.fromJson<T>(response.body?.string(), type)
                    callBack.onSuccess(t)
                } catch (e: Exception) {
                    callBack.onFail(e)
                }
            }
        })
        return call
    }
}

object KtHttpV3 {
    private var okHttpClient: OkHttpClient =
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor { message ->
            println(message)
        }.setLevel(HttpLoggingInterceptor.Level.BODY))
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    private var gson: Gson = Gson()
    var baseUrl = "https://wanandroid.com/"

    fun <T : Any> create(service: Class<T>): T {
        return Proxy.newProxyInstance(
            service.classLoader,
            arrayOf<Class<*>>(service)
        ) { proxy, method, args ->
            val annotations = method.annotations
            for (annotation in annotations) {
                if (annotation is Get) {
                    val url = baseUrl + annotation.value
                    return@newProxyInstance invoke<T>(url, method, args!!)
                }
            }
            return@newProxyInstance null

        } as T
    }

    private fun <T : Any> invoke(path: String, method: Method, args: Array<Any>): Any? {
        if (method.parameterAnnotations.size != args.size) return null

        var url = path
        val parameterAnnotations = method.parameterAnnotations
        for (i in parameterAnnotations.indices) {
            for (parameterAnnotation in parameterAnnotations[i]) {
                if (parameterAnnotation is Query) {
                    val key = parameterAnnotation.value
                    val value = args[i].toString()
                    if (!url.contains("?")) {
                        url += "?$key=$value"
                    } else {
                        url += "&$key=$value"
                    }
                } else if (parameterAnnotation is Path) {
                    val key = parameterAnnotation.value
                    val value = args[i].toString()

                    url = url.replace("{$key}", value)
                }
            }
        }

        val request = Request.Builder()
            .url(url)
            .build()

        val call = okHttpClient.newCall(request)

        return if (isKtCallReturn(method)) {
            val type = getTypeArgument(method)
            return KtCall<T>(call, gson, type)
        } else {
            val response = call.execute()
            val genericReturnType = method.genericReturnType
            val body = response.body
            val json = body?.string()
            gson.fromJson<Any?>(json, genericReturnType)
        }
    }
}

private fun getTypeArgument(method: Method) =
    (method.genericReturnType as ParameterizedType).actualTypeArguments[0]

private fun isKtCallReturn(method: Method) =
    com.google.gson.internal.`$Gson$Types`.getRawType(method.genericReturnType) == KtCall::class.java

fun testSync() {
    val api: ApiService3 = KtHttpV3.create(ApiService3::class.java)
    val data: PageResult = api.listArticleSync(0, "guanpj", 3)
    println(data)
}

fun testAsync() {
    KtHttpV3.create(ApiService3::class.java).listArticleAsync(
        0, "guanpj", 3
    ).call(object : CallBack<PageResult> {
        override fun onSuccess(data: PageResult) {
            println(data)
        }

        override fun onFail(throwable: Throwable) {
            println(throwable)
        }
    })
}

fun main() {
    testSync()
    //testAsync()
}

