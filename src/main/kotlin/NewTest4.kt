import kotlinx.coroutines.*

fun main() = runBlocking {
    launch {
        val res = equalAB("a", "b")
        println("---res:$res")
    }
    println("---end")
}