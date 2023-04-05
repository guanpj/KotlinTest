package kthttp

import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.onSuccess
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kthttp.anno.Get
import kthttp.anno.Path
import kthttp.anno.Query
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Proxy
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface ApiService5 {
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

object KtHttpV5 {
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

fun <T : Any> KtCall<T>.asFlow(): Flow<T> = callbackFlow {

    /*val job = launch {
        println("Coroutine start")
        delay(3000L)
        println("Coroutine end")
    }

    job.invokeOnCompletion {
        println("Coroutine completed $it")
    }*/

    val call = call(object: CallBack<T> {
        override fun onSuccess(data: T) {
            trySendBlocking(data)
                .onSuccess { close() }
                .onFailure {
                    //close(it)
                    cancel(CancellationException("Send channel fail!", it))
                }
        }

        override fun onFail(throwable: Throwable) {
            //close(throwable)
            cancel(CancellationException("Request fail!", throwable))
        }
    })

    awaitClose {
        call.cancel()
    }
}

fun <T : Any> KtCall<T>.asFlowTest(value: T): Flow<T> = callbackFlow {
    fun test(callback: CallBack<T>) {
        thread(isDaemon = true) {
            Thread.sleep(2000L)
            callback.onSuccess(value)
        }
    }

    println("Start")
    test(object : CallBack<T> {
        override fun onSuccess(data: T) {
            trySendBlocking(data)
                .onSuccess {
                    println("Send success")
//                    close()
                }
                .onFailure {
                    close()
                }
        }

        override fun onFail(throwable: Throwable) {
            close(throwable)
        }

    })

    awaitClose { }
}

suspend fun testAsFlow() =
    KtHttpV5.create(ApiService5::class.java)
        .listArticleAsync(0, "guanpj", 3)
        .asFlow()
        .catch { println("Catch:$it") }
        .collect { println(it) }

fun main() = runBlocking {
    testAsFlow()
}


