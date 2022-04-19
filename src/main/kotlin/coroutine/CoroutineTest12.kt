import kotlinx.coroutines.*
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking {

    val scope = CoroutineScope(coroutineContext + Job() + myExceptionHandler)

    scope.launch {
        async {
            delay(100L)
        }

        launch {
            delay(100L)

            launch {
                delay(100L)
                1 / 0
                /*try {
                    1 / 0 // 故意制造异常
                } catch (e: Exception) {
                    println("Exception: ${e.message}")
                    //throw e
                }*/
            }
        }

        delay(100L)
    }

    delay(1000L)
    println("End")
}