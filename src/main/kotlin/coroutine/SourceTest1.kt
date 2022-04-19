package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
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
    withContext(EmptyCoroutineContext) {
        delay(1000)
    }
    return s.length
}

/*
fun main() = runBlocking {
    println(getLength("abc"))
}*/

fun main() {
    //val function = ::getLength as (String, Continuation<Int>) -> Any?
    val function = ::getLength1 as KFunction2<String, Continuation<Int>, Any?>
    function("abc", object : Continuation<Int> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Int>) {
            println(result.getOrNull())
        }
    })
}