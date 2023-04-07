package course

import kotlin.concurrent.thread
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.resume

suspend fun main() {
    //println(testNoSuspendCoroutine())
    println(testSuspendCoroutine())
}

/*suspend fun testNoSuspendCoroutine() = suspendCoroutineUninterceptedOrReturn<String> {
    return@suspendCoroutineUninterceptedOrReturn "abc"
}*/

suspend fun testSuspendCoroutine() = suspendCoroutineUninterceptedOrReturn<String> {
    thread {
        Thread.sleep(1000)
        it.resume("hhh")
    }
    return@suspendCoroutineUninterceptedOrReturn COROUTINE_SUSPENDED
}
