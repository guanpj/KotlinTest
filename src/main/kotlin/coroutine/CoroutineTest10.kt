import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/*
fun main() = runBlocking<Unit> {
    var i = 0
    launch(Dispatchers.Default) {
        repeat(1000) {
            i++
        }
    }
    delay(2000)
    println(i)
}*/

/*fun main() = runBlocking<Unit> {
    var i = 0
    val jobs = mutableListOf<Job>()
    val lock = Any()
    repeat(10) {
        val job = launch(mySingleDispatcher) {
            repeat(1000) {
                i++
            }
        }
        jobs.add(job)
    }
    jobs.joinAll()
    println(i)
}*/

/*fun main() = runBlocking<Unit> {
    var i = 0
    val jobs = mutableListOf<Job>()
    val mutex = Mutex()
    repeat(10) {
        val job = launch(Dispatchers.Default) {
            repeat(1000) {
                mutex.withLock {
                    i++
                }
            }
        }
        jobs.add(job)
    }
    jobs.joinAll()
    println(i)
}*/

/*fun main() = runBlocking {
    val deferreds = mutableListOf<Deferred<Int>>()
    repeat(10) {
        val deferred = async(Dispatchers.Default) {
            var i = 0
            repeat(1000) {
                i++
            }
            return@async i
        }
        deferreds.add(deferred)
    }

    val sum = deferreds.sumOf { it.await() }
    println(sum)
}*/

fun main() = runBlocking {
    val result = (1..10).map {
        async(Dispatchers.Default) {
            var i = 0
            repeat(1000) {
                i++
            }
            return@async i
        }
    }.sumOf { it.await() }
    println(result)
}
