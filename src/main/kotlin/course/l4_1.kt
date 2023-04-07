package course

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.coroutines.*

fun main() = runBlocking {
    block.createCoroutine(MyContinuation()).resume(Unit)
}

val block = suspend {
    val a = hello2()
    a
}

private fun testCreateCoroutine() {
    val coroutine = block.createCoroutine(MyContinuation())
    coroutine.resume(Unit)
}

suspend fun hello2() = suspendCoroutine<Int> {
    thread {
        Thread.sleep(1000)
        it.resume(10086)
    }
}

/*suspend fun hello3() = withContext(Dispatchers.IO) {
    delay(2000)
    println("hello3")
}*/

class MyContinuation : Continuation<Int> {
    override val context: CoroutineContext = CoroutineName("Co-01")
    override fun resumeWith(result: Result<Int>) {
        println("MyContinuation resumeWith 结果 = ${result.getOrNull()}")
    }
}