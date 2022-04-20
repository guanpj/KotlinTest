@file:JvmName("SourceTest1Kt")

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


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
    return "$user friend:Tom, Jack"
}

//挂起函数
// ↓
suspend fun getFeedList(list: String): String {
    withContext(Dispatchers.IO) {
        delay(1000L)
    }
    return "$list feed:1,2,3"
}

suspend fun testCoroutine() {
    logX("start")
    val user = getUserInfo()
    logX(user)
    val friendList = getFriendList(user)
    logX(friendList)
    val feedList = getFeedList(friendList)
    logX(feedList)
}

fun main() = runBlocking {
    testCoroutine()
}