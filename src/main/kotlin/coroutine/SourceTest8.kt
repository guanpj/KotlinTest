package coroutine

import kotlinx.coroutines.delay
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