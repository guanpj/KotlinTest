package coroutine

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.*

fun main() = runBlocking {
    testCreateCoroutine()
    Thread.sleep(20000L)
}

private val block: suspend () -> Unit = {
    println("Hello!")
    delay(5000L)
    println("World!")
}

private fun testCreateCoroutine() {
    val coroutine = block.createCoroutine(MyContinuation())
    coroutine.resume(Unit)
}

class MyContinuation<T> : Continuation<T> {
    override val context: CoroutineContext = CoroutineName("Co-01")
    override fun resumeWith(result: Result<T>) {
        println("MyContinuation resumeWith 结果 = ${result.getOrNull()}")
    }
}
