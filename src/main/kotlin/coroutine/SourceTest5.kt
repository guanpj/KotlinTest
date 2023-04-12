package coroutine

import kotlin.concurrent.thread
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.resume

suspend fun main() {
    val result = testSuspendCoroutineUninterceptedOrReturn("abc")
    println(result)
}

suspend fun testSuspendCoroutineUninterceptedOrReturn(s: String) = suspendCoroutineUninterceptedOrReturn<String> {
    println(it)
    thread {
        Thread.sleep(1000)
        it.resume("result:$s")
    }
    return@suspendCoroutineUninterceptedOrReturn COROUTINE_SUSPENDED
}