package coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun main() {
    val result = testSuspendWithContext("aaa")
    val result1 = testSuspendCoroutine("Kotlin")
    val result2 = testSuspendCancellableCoroutine("Java")
    println(result2)
}

suspend fun testSuspendCoroutine(string: String): Int = suspendCoroutine {
    println("context:${it.context}")
    thread {
        Thread.sleep(1000)
        it.resume(string.length)
    }
}

suspend fun testSuspendCancellableCoroutine(string: String): Int = suspendCancellableCoroutine {
    thread {
        Thread.sleep(1000)
        it.resume(string.length)
    }
    it.invokeOnCancellation {
        println("Call cancelled!")
    }
}

suspend fun testSuspendWithContext(string: String): Int {
    println("context:$coroutineContext")
    withContext(Dispatchers.IO) {
        delay(1000L)
    }
    return string.length
}
