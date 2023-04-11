package coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun main() {
    val result = testSuspendCoroutine("Kotlin")
    println(result)
}

suspend fun testSuspendCoroutine1(string: String): Int = suspendCoroutine {
    println("context:${it.context}")
    thread {
        Thread.sleep(1000)
        it.resume(string.length)
    }
}

suspend fun testSuspendCoroutine(string: String): Int {
    println("context:$coroutineContext")
    withContext(Dispatchers.IO) {
        delay(1000L)
    }
    return string.length
}
