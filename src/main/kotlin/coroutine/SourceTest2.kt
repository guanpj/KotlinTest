package coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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