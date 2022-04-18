import kotlinx.coroutines.delay
import kotlin.concurrent.thread
import kotlinx.coroutines.*
import kotlin.coroutines.suspendCoroutine

/*
fun main() {
    println(Thread.currentThread().name)
    thread {
        println(Thread.currentThread().name)
        Thread.sleep(100)
    }
    Thread.sleep(1000L)
}*/

/*fun main() = runBlocking {
    println(Thread.currentThread().name)
    launch {
        println(Thread.currentThread().name)
        delay(100L)
    }
    Thread.sleep(1000L)
}*/

fun main() = runBlocking(Dispatchers.IO) {
    repeat(3) {
        println("outer time$it")
        launch {
            repeat(3) {
                println("inner time$it")
                println(Thread.currentThread().name)
                delay(2000)
            }
        }
    }
    //delay(5000L)
}

/*fun main() {
    repeat(3) {
        Thread.sleep(1000L)
        println ("Print-1:${Thread.currentThread().name}")
    }

    repeat (3) {
        Thread.sleep(900L)
        println ("Print-2:${Thread.currentThread().name}")
    }
}*/


/*fun main() = runBlocking {
    launch {
        repeat(3) {
            delay(1000L)
            println("Print-1:${Thread.currentThread().name}")
        }
    }

    launch {
        repeat(3) {
            delay(900L)
            println("Print-2:${Thread.currentThread().name}")
        }
    }
    delay(3000L)
}*/


/*fun main() = runBlocking {
    launch {
        repeat(3) {
            Thread.sleep(1000L)
            println("Print-1:${Thread.currentThread().name}")
        }
    }

    launch {
        repeat(3) {
            Thread.sleep(900L)
            println("Print-2:${Thread.currentThread().name}")
        }
    }
    delay(3000L)
}*/



 