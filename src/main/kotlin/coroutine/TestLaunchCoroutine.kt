package coroutine

import kotlinx.coroutines.*

fun main() {
    testLaunch()
    Thread.sleep(2000L)
}

private fun testLaunch() {
    GlobalScope.launch {
        println("Hello!")
        delay(1000L)
        println("World!")
    }
}

private suspend fun testLaunch2() {
    coroutineScope {
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