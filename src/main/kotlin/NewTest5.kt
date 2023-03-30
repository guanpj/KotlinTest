import kotlinx.coroutines.*

fun main() {
    println("---start")
    val coroutineScope = CoroutineScope(Dispatchers.IO)
    coroutineScope.launch {
        val res = equalAB("a", "b")
        println("---res:$res")
    }
    println("---end")
}