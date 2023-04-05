package kthttp

import com.google.gson.Gson
import kthttp.anno.Query
import kthttp.anno.Get
import kthttp.anno.Path
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.TimeUnit

interface ApiService {
    @Get("article/list/{page_num}/json")
    fun listArticle(@Path("page_num") pageNum: Int, @Query("author") author: String, @Query("page_size") pageSize: Int): PageResult
}

object KtHttpV1 {
    private var okHttpClient: OkHttpClient =
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor { message ->
            println(message)
        }.setLevel(HttpLoggingInterceptor.Level.BODY))
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    private var gson: Gson = Gson()
    var baseUrl = "https://wanandroid.com/"

    fun <T> create(service: Class<T>): T {
        return Proxy.newProxyInstance(
            service.classLoader,
            arrayOf<Class<*>>(service)
        ) { proxy, method, args ->
            val annotations = method.annotations
            for (annotation in annotations) {
                if (annotation is Get) {
                    val url = baseUrl + annotation.value
                    return@newProxyInstance invoke(url, method, args!!)
                }
            }
            return@newProxyInstance null

        } as T
    }

    private fun invoke(path: String, method: Method, args: Array<Any>): Any? {
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

        val response = okHttpClient.newCall(request).execute()

        val genericReturnType = method.genericReturnType
        val body = response.body
        val json = body?.string()
        val result = gson.fromJson<Any?>(json, genericReturnType)

        return result
    }
}

fun main() {
    val api: ApiService = KtHttpV1.create(ApiService::class.java)
    val data: PageResult = api.listArticle( 0, "guanpj", 3)
    println(data)
}

