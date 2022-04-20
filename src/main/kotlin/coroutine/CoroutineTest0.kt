package coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

suspend fun testContext() = coroutineContext

suspend fun getUser1(): String {
    withContext (Dispatchers.IO) {
        delay (1000L)
    }
    return "BoyCoder"
}