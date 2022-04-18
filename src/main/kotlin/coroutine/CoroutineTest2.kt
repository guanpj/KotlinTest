import kotlinx.coroutines.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun CoroutineScope.abc(block: suspend () -> Unit) {
    launch {
        block.invoke()
    }
}

// 不必关心代码逻辑，关心输出结果即可
fun main() {
    var f : () -> Unit = ::def
    var f1: suspend () -> Unit = ::xyz

    GlobalScope.abc(f1)
    GlobalScope.abc(f)

    GlobalScope.launch(Dispatchers.IO) {
        println("Coroutine started:${Thread.currentThread().name}")
        delay(1000L)
        println("Hello World!")
    }

    println("After launch:${Thread.currentThread().name}")
    Thread.sleep(2000L)
}

fun def() {
    println("def")
}

suspend fun xyz() {
    withContext(Dispatchers.IO) {
        println("def")
    }
}


/*fun main() = runBlocking {
    println("In runBlocking:${Thread.currentThread().name}")

    val deferred: Deferred<String> = async {
        println("In async:${Thread.currentThread().name}")
        delay(1000L) // 模拟耗时操作
        return@async "Task completed!"
    }

    println("After async:${Thread.currentThread().name}")

    val result = deferred.await()
    println("Result is: $result")
}*/
/*
输出结果：
In runBlocking:main @coroutine#1
After async:main @coroutine#1 // 注意，它比“In async”先输出
In async:main @coroutine#2
Result is: Task completed!
*/
