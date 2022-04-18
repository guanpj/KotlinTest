import kotlinx.coroutines.*
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


// 代码段3

/*
suspend fun main() {
    val user = getUserInfo()
    logX(user)
}
*/

val myDispatcher = Executors.newSingleThreadExecutor {
    Thread(it, "MyDispatcher").apply { isDaemon = true }
}.asCoroutineDispatcher()

/*
fun main() = runBlocking(myDispatcher) {
    val user = getUserInfo()
    logX(user)
}*/


// 代码段7

fun main() = runBlocking {
    runBlocking {

    }

    logX("Before launch.") // 1
    launch(Dispatchers.Unconfined) {
        logX("In launch.") // 2
        delay(1000L)
        logX("End launch.") // 3
    }
    logX("After launch")   // 4
}
