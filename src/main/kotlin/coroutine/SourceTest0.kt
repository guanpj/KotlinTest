package coroutine

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    GlobalScope.launch {
        logX("Hello")
        delay(1000)
        logX("World")
    }
}
