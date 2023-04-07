package course

import kotlin.concurrent.thread
import kotlin.coroutines.*

fun main() {
    val func = ::getLengthSuspend as (String, Continuation<Int>) -> Any?
    func("Java", object : Continuation<Int> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Int>) {
            println(result.getOrNull())
        }
    })

    Thread.sleep(3000)
}
