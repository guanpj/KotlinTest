package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import logX
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.*

suspend fun getLength1(s: String): Int = suspendCoroutine {
    thread {
        Thread.sleep(1000)
        it.resume(s.length)
    }
}

suspend fun getLength2(s: String): Int {
    logX("getLength2")
    withContext(EmptyCoroutineContext) {
        logX("delay")
        delay(1000)
    }
    logX("res: ${s.length}")
    return s.length
}

/*fun main() = runBlocking {
    println(getLength1("abc"))
    println(getLength2("def"))
}*/

fun main() = runBlocking<Unit> {

    val continuation = object : Continuation<Int> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Int>) {
            println(result.getOrNull())
        }
    }

    val function = ::getLength1 as (String, Continuation<Int>) -> Any?
    //val function = ::getLength1 as KFunction2<String, Continuation<Int>, Any?>

    //val function2 = ::getLength2 as (String, Continuation<Int>) -> Any?
    //val function2 = ::getLength2 as KFunction2<String, Continuation<Int>, Any?>

    function("abc", continuation)
}
