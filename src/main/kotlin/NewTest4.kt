import kotlinx.coroutines.*

fun main() = runBlocking {
    println("---start")
    launch {
        val res = equalAB("a", "b")
        println("---res:$res")
    }
    println("---end")
}