package coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.*

val susBlock = suspend {
    println("hello")
    delay(1000)
    println("world")
    "result"
}

suspend fun susFun(p1: Int): String {
    println("hello")
    delay(1000)
    return "world : $p1"
}

fun testStartCoroutine() {
    val continuation = object : Continuation<String> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<String>) {
            println("Result: ${result.getOrNull()}")
        }
    }
    susBlock.startCoroutine(continuation)

    ::susFun.startCoroutine(1, continuation)
}

fun main() {
    testStartCoroutine()
    Thread.sleep(2000)
}