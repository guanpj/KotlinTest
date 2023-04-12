package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println(Thread.currentThread().name)

    launch {
        println(Thread.currentThread().name)
        delay(100L)
    }

    Thread.sleep(1000L)
}
