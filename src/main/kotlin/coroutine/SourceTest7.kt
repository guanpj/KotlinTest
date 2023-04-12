package coroutine

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.concurrent.thread
import kotlin.coroutines.resume

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