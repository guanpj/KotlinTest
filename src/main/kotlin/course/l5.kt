package course

import kotlinx.coroutines.delay
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine


fun main() {
    testStartCoroutineForSuspend()
    Thread.sleep(2000L)
}
private suspend fun func(): String {
    println("Hello!")
    delay(1000L)
    println("World!")
    return "Result"
}

private fun testStartCoroutineForSuspend() {
    val block = ::func
    block.startCoroutine(MyContinuation())
}