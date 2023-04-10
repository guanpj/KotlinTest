package course

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


// 代码段1

fun main() {
    val scope = CoroutineScope(Job())
    scope.launch {
        testFlow()
    }

    Thread.sleep(1000L)

    logX("end")
}

private suspend fun testFlow() {
    // 1
    flow {
        emit(1)
        emit(2)
        emit(3)
        emit(4)
        emit(5)
    }.collect {      // 2
        logX(it)
    }
}


class A {
    val b = 1
}

// 代码段6

// 1
public val A.c: String
    get() = "aaa"

/**
 * 控制台输出带协程信息的log
 */
fun logX(any: Any?) {
    println(
        """
================================
$any
Thread:${Thread.currentThread().name}
================================""".trimIndent()
    )
}

/*
输出结果
================================
1
Thread:DefaultDispatcher-worker-1
================================
================================
2
Thread:DefaultDispatcher-worker-1
================================
================================
3
Thread:DefaultDispatcher-worker-1
================================
================================
4
Thread:DefaultDispatcher-worker-1
================================
================================
5
Thread:DefaultDispatcher-worker-1
================================
================================
end
Thread:main
================================
*/