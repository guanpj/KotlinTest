package coroutine

import kotlinx.coroutines.delay
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun main() {
    val result = testSuspendCoroutine("Kotlin")
    println(result)
}

suspend fun testSuspendCoroutine(string: String): Int = suspendCoroutine {
    thread {
        Thread.sleep(2000)
        it.resume(string.length)
    }
}