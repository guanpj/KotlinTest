package coroutine

import course.getLengthSuspend
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.coroutines.*
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn

fun main() {
    val func = ::testSuspendCoroutine as (String, Continuation<Int>) -> Any?
    val myContinuation = MyContinuation<Int>()
    func("Java", myContinuation)
    Thread.sleep(3000)
}