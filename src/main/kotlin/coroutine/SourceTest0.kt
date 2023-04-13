import coroutine.logX
import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    launch {
        repeat(3) {
            println("job1 repeat $it times")
            yield()
        }
    }
    launch {
        repeat(3) {
            println("job2 repeat $it times")
            yield()
        }
    }
}
