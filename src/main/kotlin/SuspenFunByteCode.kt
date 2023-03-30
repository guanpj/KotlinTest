import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

suspend fun testSuspendWithContext() {
    withContext(Dispatchers.IO) {
        delay(1000)
        delay(2000)
    }
}