package coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.coroutines.*
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn

suspend fun main() {
    println(testSuspendCoroutineUninterceptedOrReturn())
}

suspend fun testSuspendCoroutineUninterceptedOrReturn() = suspendCoroutineUninterceptedOrReturn<String> {
    thread {
        Thread.sleep(1000)
        it.resume("hhh")
    }
    return@suspendCoroutineUninterceptedOrReturn COROUTINE_SUSPENDED
}