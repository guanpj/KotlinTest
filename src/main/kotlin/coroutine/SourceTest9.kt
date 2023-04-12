package coroutine

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.delay
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.startCoroutine

fun main() {
    testStartCoroutine()
    Thread.sleep(2000L)
}

val block: suspend () -> String = {
    println("Hello!")
    delay(1000L)
    println("World!")
    "Result"
}

private fun testStartCoroutine() {
    block.startCoroutine(MyContinuation())
}

class MyContinuation<T> : Continuation<T> {
    override val context: CoroutineContext = CoroutineName("Co-01") + LogInterceptor()
    override fun resumeWith(result: Result<T>) {
        println("MyContinuation resumeWith 结果 = ${result.getOrNull()}")
    }
}

class LogInterceptor : ContinuationInterceptor {
    override val key = ContinuationInterceptor

    override fun <T> interceptContinuation(continuation: Continuation<T>)
            = LogContinuation(continuation)
}

class LogContinuation<T>(private val continuation: Continuation<T>)
    : Continuation<T> by continuation {
    override fun resumeWith(result: Result<T>) {
        println("before resumeWith: $result")
        continuation.resumeWith(result)
        println("after resumeWith.")
    }
}
