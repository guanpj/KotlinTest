import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

suspend fun getUserInfo(): String {
    logX("Before IO Context.")
    withContext (Dispatchers.IO) {
        logX("In IO Context.")
        delay (1000L)
    }
    logX ("After IO Context.")
    return "BoyCoder"
}

fun main() = runBlocking {
    val userInfo = getUserInfo()
    println(userInfo)
}