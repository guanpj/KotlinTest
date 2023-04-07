package course

import kotlin.concurrent.thread
import kotlin.coroutines.*

suspend fun main() {
    val result = getLengthSuspend("Kotlin")
    println(result)
}

suspend fun getLengthSuspend(string: String): Int = suspendCoroutine {
    thread {
        Thread.sleep(2000)
        it.resume(string.length)
    }
}