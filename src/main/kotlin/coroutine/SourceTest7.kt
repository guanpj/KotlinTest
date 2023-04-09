package coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.concurrent.thread
import kotlin.coroutines.*
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn

suspend fun main() {
    testSuspendCancellableCoroutine()
}

suspend fun testSuspendCancellableCoroutine(): String = suspendCancellableCoroutine {
    thread {
        Thread.sleep(1000)
        it.resume("hhh")
    }
    it.invokeOnCancellation {
        println("Call cancelled!")
    }
}