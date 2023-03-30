import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

suspend fun testSuspendEmpty() {

}

suspend fun testSuspend() {
    delay(1000)
}

suspend fun testSuspendReturn(): String {
    delay(1000)
    return "abc"
}