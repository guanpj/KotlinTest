package kthttp

import com.google.gson.Gson
import com.google.gson.internal.`$Gson$Types`.getRawType
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kthttp.anno.Get
import kthttp.anno.Path
import kthttp.anno.Query
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.http.Field
import retrofit2.http.GET
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Proxy

// https://trendings.herokuapp.com/repo?lang=java&since=weekly

interface ApiServiceV6 {
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

    @Get("article/list/{page_num}/json")
    fun listArticleFlow(
        @Path("page_num") pageNum: Int,
        @Query("author") author: String,
        @Query("page_size") pageSize: Int
    ): Flow<PageResult>
}

object KtHttp {

    private var okHttpClient: OkHttpClient = OkHttpClient()
    private var gson: Gson = Gson()
    var baseUrl = "https://wanandroid.com/"

    fun <T : Any> create(service: Class<T>): T {
        return Proxy.newProxyInstance(
            service.classLoader,
            arrayOf<Class<*>>(service)
        ) { proxy, method, args ->
            val annotations = method.annotations
            for (annotation in annotations) {
                if (annotation is GET) {
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
                if (parameterAnnotation is Field) {
                    val key = parameterAnnotation.value
                    val value = args[i].toString()
                    if (!url.contains("?")) {
                        url += "?$key=$value"
                    } else {
                        url += "&$key=$value"
                    }

                }
            }
        }

        val request = Request.Builder()
            .url(url)
            .build()

        val call = okHttpClient.newCall(request)

        return when {
            isKtCallReturn(method) -> {
                val genericReturnType = getTypeArgument(method)
                KtCall<T>(call, gson, genericReturnType)
            }
            isFlowReturn(method) -> {
                flow<T> {
                    val genericReturnType = getTypeArgument(method)
                    val response = okHttpClient.newCall(request).execute()
                    val json = response.body?.string()
                    val result = gson.fromJson<T>(json, genericReturnType)
                    emit(result)
                }
            }
            else -> {
                val response = okHttpClient.newCall(request).execute()

                val genericReturnType = method.genericReturnType
                val json = response.body?.string()
                gson.fromJson(json, genericReturnType)
            }
        }
    }

    private fun getTypeArgument(method: Method) =
        (method.genericReturnType as ParameterizedType).actualTypeArguments[0]

    private fun isKtCallReturn(method: Method) =
        getRawType(method.genericReturnType) == KtCall::class.java

    private fun isFlowReturn(method: Method) =
        getRawType(method.genericReturnType) == Flow::class.java

}

fun main() {
    // 协程作用域外
    val flow = KtHttpV6.create(ApiServiceV6::class.java)
        .listArticleFlow(0, "guanpj", 3)
        .flowOn(Dispatchers.IO)
        .catch { println("Catch: $it") }

    runBlocking {
        // 协程作用域内
        flow.collect {
            println(it)
        }
    }
}


private suspend fun testFlow() =
    KtHttpV6.create(ApiServiceV6::class.java)
        .listArticleFlow(0, "guanpj", 3)
        .flowOn(Dispatchers.IO)
        .catch { println("Catch: $it") }
        .collect {
            println(it)
        }