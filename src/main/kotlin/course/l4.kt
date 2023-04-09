package course

import kotlinx.coroutines.*


// 代码中一共启动了两个协程
/*fun main() = runBlocking {
    println(Thread.currentThread().name)

    launch {
        println(Thread.currentThread().name)
        delay(100L)
    }

    Thread.sleep(1000L)
}*/

/*
输出结果：
main @coroutine#1
main @coroutine#2

这里要配置特殊的VM参数：-Dkotlinx.coroutines.debug
这样一来，Thread.currentThread().name就能会包含：协程的名字@coroutine#1
*/

fun main() {
    testLaunch()
    Thread.sleep(2000L)
}

private fun testLaunch() {
    val scope = CoroutineScope(Job())
    scope.launch {
        println("Hello!")
        delay(1000L)
        println("World!")
    }
}

/*
输出结果：
Hello!
World!
*/



