package coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.concurrent.thread
import kotlin.coroutines.*
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn

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