package kthttp

import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kthttp.anno.Get
import kthttp.anno.Path
import kthttp.anno.Query
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Proxy
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.javaType
import kotlin.reflect.jvm.kotlinFunction

interface ApiService7 {
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

    @Get("article/list/{page_num}/json")
    suspend fun listArticleSuspend(
        @Path("page_num") pageNum: Int,
        @Query("author") author: String,
        @Query("page_size") pageSize: Int
    ): PageResult
}

object KtHttpV7 {
    private var okHttpClient: OkHttpClient =
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor { message ->
            println(message)
        }.setLevel(HttpLoggingInterceptor.Level.BODY))
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
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

        return when {
            isKtCallReturn(method) -> {
                val type = getTypeArgument(method)
                KtCall<T>(call, gson, type)
            }

            isFlowReturn(method) -> {
                flow<T> {
                    val response = call.execute()
                    val genericReturnType = method.genericReturnType
                    val body = response.body
                    val json = body?.string()
                    val result = gson.fromJson<T>(json, genericReturnType)
                    emit(result)
                }
            }

            isSuspend(method) -> {
                val continuation = args.last() as? Continuation<T>

                val genericReturnType = method.kotlinFunction?.returnType?.javaType ?: throw IllegalStateException()

                // 使用临时方法消除泛型
                /*val func = ::temp as (Call, Gson, Type, Continuation<T>?) -> Any?
                func.invoke(call, gson, genericReturnType, continuation)*/

                val func = KtHttpV7::class.getGenericFunction("realCall")
                // 反射调用realCall()
                func.call(this, call, gson, genericReturnType, continuation)
            }

            else -> {
                val response = call.execute()
                val genericReturnType = method.genericReturnType
                val body = response.body
                val json = body?.string()
                gson.fromJson<Any?>(json, genericReturnType)
            }
        }
    }

    suspend fun <T : Any> realCall(call: Call, gson: Gson, type: Type) : T = suspendCancellableCoroutine { continuation ->
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                continuation.resumeWithException(e)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val t = gson.fromJson<T>(response.body?.string(), type)
                    continuation.resume(t)
                } catch (e: Exception) {
                    continuation.resumeWithException(e)
                }
            }
        })
        continuation.invokeOnCancellation { call.cancel() }
    }

    suspend fun temp(call: Call, gson: Gson, type: Type) = realCall<PageResult>(call, gson, type)

    fun KClass<*>.getGenericFunction(name: String): KFunction<*> { return members.single { it.name == name } as KFunction<*> }
}

private fun getTypeArgument(method: Method) =
    (method.genericReturnType as ParameterizedType).actualTypeArguments[0]

private fun isKtCallReturn(method: Method) =
    com.google.gson.internal.`$Gson$Types`.getRawType(method.genericReturnType) == KtCall::class.java

private fun isFlowReturn(method: Method) =
    com.google.gson.internal.`$Gson$Types`.getRawType(method.genericReturnType) == Flow::class.java

//private fun isSuspend(method: Method) = method.returnType.isAssignableFrom(Deferred::class.java)
private fun isSuspend(method: Method) = method.kotlinFunction?.isSuspend ?: false

fun main() = runBlocking {
    val data = KtHttpV7.create(ApiService7::class.java)
        .listArticleSuspend(0, "guanpj", 3)
    println(data)
}



