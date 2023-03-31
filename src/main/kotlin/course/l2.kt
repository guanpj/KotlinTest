package course

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


fun main(): Unit = runBlocking {
    println("In runBlocking:${Thread.currentThread().name}")

    val deferred: Deferred<String> = async {
        println("In async:${Thread.currentThread().name}")
        delay(1500L) // 模拟耗时操作
        return@async "Task completed!"
    }

    println("After async:${Thread.currentThread().name}")

    val result = deferred.await()


    println("Result is: $result")
}
/*
输出结果：
In runBlocking:main @coroutine#1
After async:main @coroutine#1 // 注意，它比“In async”先输出
In async:main @coroutine#2
Result is: Task completed!
*/