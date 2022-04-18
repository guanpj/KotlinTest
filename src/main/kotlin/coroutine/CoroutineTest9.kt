import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// 代码段1

fun main() = runBlocking {
    var i = 0

    // Default 线程池
    launch(Dispatchers.Default) {
        repeat(1000) {
            i++
        }
    }

    delay(1000L)

    println("i = $i")
}