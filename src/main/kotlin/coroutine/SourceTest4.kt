package coroutine

import course.getLengthSuspend
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn

fun main() {
    val func = ::testSuspendCoroutine as (String, Continuation<Int>) -> Any?
    func("Java", object : Continuation<Int> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Int>) {
            println(result.getOrNull())
        }
    })

    Thread.sleep(3000)
}