package coroutine

import kotlinx.coroutines.*
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.*

fun main() {
    testLaunch()
    Thread.sleep(2000L)
}

private fun testLaunch() {
    val scope = CoroutineScope(Job())
    scope.launch {
        println("Hello!")
        delay(1000L)
        println("World!")
    }
}

private fun testLaunch1() {
    val scope = CoroutineScope(Job())
    val block: suspend CoroutineScope.() -> Unit = {
        println("Hello!")
        delay(1000L)
        println("World!")
    }
    scope.launch(block = block)
}