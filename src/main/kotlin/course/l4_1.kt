package course

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.coroutines.*

fun main() {
    testStartCoroutine()
    //testCreateCoroutine()
    Thread.sleep(2000L)
}

val block: suspend (Int) -> String = {
    println("Hello!")
    delay(1000L)
    println("World!")
    "Result"
}

private fun testStartCoroutine() {
    val completion = MyContinuation<String>()
    block.startCoroutine(1, completion)
}

/*private fun testCreateCoroutine() {
    val coroutine = block.createCoroutine(MyContinuation())
    coroutine.resume(Unit)
}*/

class MyContinuation<T> : Continuation<T> {
    override val context: CoroutineContext = CoroutineName("Co-01")
    override fun resumeWith(result: Result<T>) {
        println("MyContinuation resumeWith 结果 = ${result.getOrNull()}")
    }
}