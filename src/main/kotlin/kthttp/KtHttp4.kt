package kthttp

import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun <T: Any> KtCall<T>.await1() : T = suspendCoroutine {
    call(object : CallBack<T> {
        override fun onSuccess(data: T) {
            it.resume(data)
            //it.resumeWith(Result.success(data))
        }

        override fun onFail(throwable: Throwable) {
            it.resumeWithException(throwable)
            //it.resumeWith(Result.failure(throwable))
        }
    })
}

suspend fun <T: Any> KtCall<T>.await() : T = suspendCancellableCoroutine {
    val call = call(object : CallBack<T> {
        override fun onSuccess(data: T) {
            it.resume(data)
            //it.resumeWith(Result.success(data))
        }

        override fun onFail(throwable: Throwable) {
            it.resumeWithException(throwable)
            //it.resumeWith(Result.failure(throwable))
        }
    })

    it.invokeOnCancellation {
        println("Call cancelled!")
        call.cancel()
    }
}

fun main() = runBlocking {
    val ktCall = KtHttpV3.create(ApiService3::class.java)
        .listArticleAsync(0, "guanpj", 3)

    val result = ktCall.await()
    println(result)
}

//fun main() = runBlocking {
//    val start = System.currentTimeMillis()
//    val deferred = async {
//        KtHttpV3.create(ApiService3::class.java).listArticleAsync(0, "guanpj", 3).await()
//    }
//    deferred.invokeOnCompletion {
//        println("invokeOnCompletion!")
//    }
//    delay(50L)
//
//    deferred.cancel()
//    println("Time cancel: ${System.currentTimeMillis() - start}")
//
//    try {
//        println(deferred.await())
//    } catch (e: Exception) {
//        println("Time exception: ${System.currentTimeMillis() - start}")
//        println("Catch exception:$e")
//    } finally {
//        println("Time total: ${System.currentTimeMillis() - start}")
//    }
//}

