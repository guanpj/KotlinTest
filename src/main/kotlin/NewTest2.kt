import kotlinx.coroutines.*

suspend fun main() {
    println("---start")
    coroutineScope {
        launch {
            val res = equalAB("a", "b")
            println("---res:$res")
        }
    }
    println("---end")
}
