package coroutine

import kotlin.coroutines.Continuation

fun main() {
    val func = ::testSuspendCoroutine as (String, Continuation<Int>) -> Any?
    val myContinuation = MyContinuation<Int>()
    func("Java", myContinuation)
    Thread.sleep(3000)
}