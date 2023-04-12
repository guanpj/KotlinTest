package coroutine

import kotlin.coroutines.*

fun main() {
    testCreateCoroutine()
    Thread.sleep(2000L)
}

private fun testCreateCoroutine() {
    val coroutine = block.createCoroutine(MyContinuation())
    coroutine.resume(Unit)
}
