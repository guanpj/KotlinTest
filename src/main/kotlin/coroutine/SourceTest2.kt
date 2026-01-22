package coroutine

import kotlinx.coroutines.*

fun main() {
    GlobalScope.launch {
        println("Hello")
        delay(5000)
        println("World")
    }
    Thread.sleep(20000)
}
