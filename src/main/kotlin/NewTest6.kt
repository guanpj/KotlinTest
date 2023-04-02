import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

suspend fun main() = coroutineScope {
    println("---start")
    launch {
        val res = equalAB("a", "b")
        println("---res:$res")
    }
    println("---end")
}
