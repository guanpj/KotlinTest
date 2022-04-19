import kotlinx.coroutines.*
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking {
    val parentJob = launch(mySingleDispatcher) {
        launch {
            var i = 0
            while (true) {
                try {
                    delay(500L)
                } catch (e: CancellationException) {
                    println("Catch CancellationException")
                    // 1，注意这里
                    throw e
                }
                i ++
                println("First i = $i")
            }
        }

        launch {
            var i = 0
            while (true) {
                delay(500L)
                i ++
                println("Second i = $i")
            }
        }
    }

    delay(2000L)

    parentJob.cancel()
    parentJob.join()

    println("End")
}

/*fun main() = runBlocking {
    var deferred: Deferred<Unit>? = null
    try {
        deferred = async {
            delay(100L)
            1 / 0
        }
    } catch (e: ArithmeticException) {
        println("Catch: $e")
    }

    deferred?.await()

    delay(500L)
    println("End")
}*/


/*fun main() = runBlocking {

    launch {
        try {
            delay(100L)
            1 / 0 // 故意制造异常
        } catch (e: ArithmeticException) {
            println("Catch: $e")
        }
    }

    delay(500L)
    println("End")
}*/

/*fun main() = runBlocking {
    val deferred = async {
        delay(100L)
        1 / 0
    }

    try {
        deferred.await()
    } catch (e: ArithmeticException) {
        println("Catch: $e")
    }

    delay(500L)
    println("End")
}*/

/*fun main() = runBlocking {
    val scope = CoroutineScope(SupervisorJob())
    // 变化在这里
    val deferred = scope.async {
        delay(100L)
        1 / 0
    }

    try {
        deferred.await()
    } catch (e: ArithmeticException) {
        println("Catch: $e")
    }

    delay(500L)
    println("End")
}*/
