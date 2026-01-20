package coroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis


fun main() = runBlocking {
    suspend fun getResult1(): String {
        logX("Start getResult1")
        delay(1000L) // 模拟耗时操作
        logX("End getResult1")
        return "Result1"
    }

    suspend fun getResult2(): String {
        logX("Start getResult2")
        delay(1000L) // 模拟耗时操作
        logX("End getResult2")
        return "Result2"
    }

    suspend fun getResult3(): String {
        logX("Start getResult3")
        delay(1000L) // 模拟耗时操作
        logX("End getResult3")
        return "Result3"
    }

    val results: List<String>

    val time = measureTimeMillis {
        val result1 = async { getResult1() }
        val result2 = async { getResult2() }
        val result3 = async { getResult3() }

        results = listOf(result1.await(), result2.await(), result3.await())
    }

    println("Time: $time")
    println(results)
}