package coroutine

import course.MyContinuation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import logX
import kotlin.concurrent.thread
import kotlin.coroutines.*
import kotlin.reflect.*

fun main() = runBlocking {
    println(Thread.currentThread().name)

    launch {
        println(Thread.currentThread().name)
        delay(100L)
    }

    Thread.sleep(1000L)
}
