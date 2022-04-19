import kotlinx.coroutines.*
import java.util.concurrent.Executors
import kotlin.coroutines.suspendCoroutine

object SuspendFromJavaExample {
    // 在Java当中如何调用这个方法？
    suspend fun getUserInfo(id: Long):String {
        delay(1000L)
        return "Kotlin"
    }
}

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

suspend fun load() = suspendCoroutine<String> {

}