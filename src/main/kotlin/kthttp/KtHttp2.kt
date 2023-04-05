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

interface ApiService2 {
    @Get("article/list/{page_num}/json")
    fun listArticle(@Path("page_num") pageNum: Int, @Query("author") author: String, @Query("page_size") pageSize: Int): PageResult
}

object KtHttpV2 {
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor { message -> println(message) }
            .setLevel(HttpLoggingInterceptor.Level.BODY))
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    private val gson: Gson by lazy { Gson() }
    var baseUrl = "https://wanandroid.com/"

    inline fun <reified T> create(): T {
        return Proxy.newProxyInstance(
            T::class.java.classLoader,
            arrayOf(T::class.java)
        ) { proxy, method, args ->
            return@newProxyInstance method.annotations
                .filterIsInstance<Get>()
                .takeIf { it.size == 1 }
                ?.let {
                    invoke("$baseUrl${it[0].value}", method, args!!)
                }
        } as T
    }

    fun invoke(url: String, method: Method, args: Array<Any>): Any? =
        method.parameterAnnotations.takeIf { it.size == args.size }
            ?.mapIndexed { index, annotation -> Pair(annotation, args[index]) }
            ?.fold(url, ::parseUrl)
            ?.let { Request.Builder().url(it).build() }
            ?.let { okHttpClient.newCall(it).execute().body?.string() }
            ?.let { gson.fromJson<Any?>(it, method.genericReturnType) }

    private fun parseUrl(acc: String, pair: Pair<Array<Annotation>, Any>): String {
        val (annoArr, arg) = pair
        return annoArr
            .filterIsInstance<Path>()
            .takeIf { it.isNotEmpty() }
            ?.let { acc.replace("{${it[0].value}}", arg.toString()) }
            ?: annoArr
                .filterIsInstance<Query>()
                .takeIf { it.isNotEmpty() }
                ?.let {
                    if (!acc.contains("?")) {
                        "$acc?${it[0].value}=$arg"
                    } else {
                        "$acc&${it[0].value}=$arg"
                    }
                }
            ?: acc
    }
}

fun main() {
    val api = KtHttpV2.create<ApiService2>()
    val data: PageResult = api.listArticle(0, "guanpj", 5)
    println(data)
}

