import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() = runBlocking {
    println("---start")
    val res = equalAB("a", "b")
    println("---res:$res")
    println("---end")
}

suspend fun equalAB(a: String, b: String): Boolean {
    val isCopySuccess = withContext(Dispatchers.IO) {
        try {
            delay(3000)
            a == b
        } catch (e: Exception) {
            false
        }
    }
    return isCopySuccess
}