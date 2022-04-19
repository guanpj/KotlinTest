package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.*

interface Callback<T> {
    fun resume(result: T)
}

fun getLength(Sting: String, callback: Callback<Int>) {
    thread {
        Thread.sleep(1000)
        callback.resume(Sting.length)
    }
}

fun main() {
    getLength("Kotlin", object : Callback<Int> {
        override fun resume(result: Int) {
            println(result)
        }
    })
}