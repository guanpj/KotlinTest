import kotlinx.coroutines.*

fun main() {
    println("---start")
    GlobalScope.launch {
        val res = equalAB("a", "b")
        println("---res:$res")
    }
    println("---end")
    Thread.sleep(6000)
}