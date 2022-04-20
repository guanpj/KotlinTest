package coroutine

import kotlinx.coroutines.delay
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

    /*val coroutine = ::susFun.createCoroutine(1, object : Continuation<String> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<String>) {
            println("Result: ${result.getOrNull()}")
        }
    })
    coroutine.resume(Unit)*/
}

fun main() {
    testStartCoroutine()
    Thread.sleep(2000)
}