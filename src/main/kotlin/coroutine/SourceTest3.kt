package coroutine

import kotlinx.coroutines.delay

object SuspendFromJavaExample {
    // 在Java当中如何调用这个方法？
    suspend fun getUserInfo(id: Long):String {
        delay(1000L)
        return "Kotlin"
    }
}