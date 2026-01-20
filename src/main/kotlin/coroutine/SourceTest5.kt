package coroutine

import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.resume

suspend fun main() {
    println(testNotSuspendCoroutineUninterceptedOrReturn())
}

suspend fun testNotSuspendCoroutineUninterceptedOrReturn() = suspendCoroutineUninterceptedOrReturn<String> {
    return@suspendCoroutineUninterceptedOrReturn "abc"
}