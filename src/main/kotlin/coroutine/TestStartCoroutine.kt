package coroutine

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.*

fun main() = runBlocking {
    testCreateCoroutine()
    //testStartCoroutine()
    //testStartCoroutineForSuspend()
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

private suspend fun func(): String {
    println("Hello!")
    delay(1000L)
    println("World!")
    return "Result"
}

private fun testStartCoroutineForSuspend() {
    ::func.startCoroutine(MyContinuation())
}

private fun testCreateCoroutine() {
    val coroutine = block.createCoroutine(MyContinuation())
    coroutine.resume(Unit)
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
