import kotlinx.coroutines.*
import kotlin.concurrent.thread
import kotlin.coroutines.*

fun main() = runBlocking {
    val b = suspend {
        val a = hello2()
        a
    }
    b.createCoroutine(MyContinuation()).resume(Unit)
    hello3()
}

suspend fun hello2() = suspendCoroutine<Int> {
    thread {
        Thread.sleep(1000)
        it.resume(10086)
    }
}

suspend fun hello3() = withContext(Dispatchers.IO) {
    delay(2000)
    println("hello3")
}

class MyContinuation : Continuation<Int> {
    override val context: CoroutineContext = CoroutineName("Co-01")
    override fun resumeWith(result: Result<Int>) {
        println("MyContinuation resumeWith 结果 = ${result.getOrNull()}")
    }
}