import kotlinx.coroutines.*
import kotlin.random.Random

fun main() = runBlocking {
    val job = launch(start = CoroutineStart.LAZY) {
        logX("Coroutine start!")
        delay(4000L)
    }
    delay(500L)
    job.log()
    job.start()
    job.log()

    //delay(500L)
    //job.cancel()
    delay(1100L)
    job.log()
    delay(2000L)
    logX("Process end!")
}

/*fun main() = runBlocking {
    suspend fun download() {
        // 模拟下载任务
        val time = (Random.nextDouble() * 1000).toLong()
        logX("Delay time: = $time")
        delay(time)
    }
    val job = launch(start = CoroutineStart.LAZY) {
        logX("Coroutine start!")
        download()
        logX("Coroutine end!")
    }
    delay(500L)
    job.log()
    job.start()
    job.log()
    job.invokeOnCompletion {
        job.log() // 协程结束以后就会调用这里的代码
    }
    job.join()      // 等待协程执行完毕
    logX("Process end!")
}*/

