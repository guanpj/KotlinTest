package coroutine

import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn

suspend fun main() {
    println(testNotSuspendCoroutineUninterceptedOrReturn())
}

suspend fun testNotSuspendCoroutineUninterceptedOrReturn() = suspendCoroutineUninterceptedOrReturn<String> {
    return@suspendCoroutineUninterceptedOrReturn "abc"
}