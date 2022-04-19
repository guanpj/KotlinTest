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
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn

suspend fun testNoSuspendCoroutine() = suspendCoroutineUninterceptedOrReturn<String> {
    return@suspendCoroutineUninterceptedOrReturn "5"
}

suspend fun testSuspendCoroutine() = suspendCoroutineUninterceptedOrReturn<String> {
    thread {
        Thread.sleep(1000)
        it.resume("hhh")
    }
    return@suspendCoroutineUninterceptedOrReturn kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
}

fun main() = runBlocking {
    println("testNoSuspendCoroutine: ${testNoSuspendCoroutine()}")
    println("testSuspendCoroutine: ${testSuspendCoroutine()}")
}