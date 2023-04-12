package coroutine

import kotlin.coroutines.*

suspend fun hello(id: Int, name: String): String {
    return suspendCoroutine { continuation ->
        // 模拟一个异步操作
        Thread.sleep(1000)

        // 设置协程的返回值并恢复协程
        val result = "Hello, $name! Your id is $id."
        continuation.resume(result)
    }
}

fun main() {
    val continuation = object : Continuation<String> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<String>) {
            // 在协程恢复时打印返回值
            println(result.getOrNull())
        }
    }

    // 使用 lambda 表达式启动协程
    val helloCoroutine: suspend () -> String = { hello(1, "a") }
    helloCoroutine.startCoroutine(continuation)
}
