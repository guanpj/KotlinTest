@file:JvmName("SourceTest1Kt")

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.coroutineContext

suspend fun getUserInfo(): String {
    withContext(Dispatchers.IO) {
        delay(1000L)
    }
    return "BoyCoder"
}

suspend fun getFriendList(user: String): String {
    withContext(Dispatchers.IO) {
        delay(1000L)
    }
    return "$user friend:Tom, Jack"
}

suspend fun testCoroutine() {
    val user = getUserInfo()
    val friendList = getFriendList(user)
    println(friendList)
}

fun main() = runBlocking {
    testCoroutine()
}