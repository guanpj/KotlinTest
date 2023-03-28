import kotlinx.coroutines.*
import java.util.concurrent.Executors
import kotlin.concurrent.thread
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.CoroutineContext

/** * 打印Job的状态信息 */
fun Job.log() {
    logX(""" 
        isActive = $isActive 
        isCancelled = $isCancelled 
        isCompleted = $isCompleted """.trimIndent())
}

/** * 控制台输出带协程信息的log */
fun logX(any: Any?) {
    println("""================================
$any 
Thread:${Thread.currentThread().name}
================================""".trimIndent())
}

val mySingleDispatcher = Executors.newSingleThreadExecutor {
    Thread(it, "myDispatcher").apply { isDaemon = true }
}.asCoroutineDispatcher()

val myExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    println("Catch exception: $throwable")
}
fun main() = runBlocking {
    println("run")
    val launch = GlobalScope.launch {
        async { }
    }
    async { }
    launch.join()
    launch.cancel()

    GlobalScope.async {

    }

    coroutineScope {

    }
}
