package course

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine


// 代码段1

fun main() = runBlocking {
    testCoroutine()
}

suspend fun testCoroutine() {
    val user = getUserInfo()
    val friendList = getFriendList(user)
    val feedList = getFeedList(user, friendList)
}

//挂起函数
// ↓
suspend fun getUserInfo(): String {
    withContext(Dispatchers.IO) {
        delay(1000L)
    }
    return "BoyCoder"
}

//挂起函数
// ↓
suspend fun getFriendList(user: String): String {
    withContext(Dispatchers.IO) {
        delay(1000L)
    }
    return "Tom, Jack"
}

//挂起函数
// ↓
suspend fun getFeedList(user: String, list: String): String {
    withContext(Dispatchers.IO) {
        delay(1000L)
    }
    return "{FeedList..}"
}