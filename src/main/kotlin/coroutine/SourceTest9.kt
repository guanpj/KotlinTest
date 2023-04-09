package coroutine

import kotlinx.coroutines.*
import kotlin.coroutines.Continuation
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
    override val context: CoroutineContext = CoroutineName("Co-01")
    override fun resumeWith(result: Result<T>) {
        println("MyContinuation resumeWith 结果 = ${result.getOrNull()}")
    }
}
