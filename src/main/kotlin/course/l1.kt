package course

import kotlinx.coroutines.runBlocking


// 看不懂代码没关系，目前咱们只需要关心代码的执行结果
fun main() = runBlocking {
    val sequence = getSequence()
    printSequence(sequence)
}

fun getSequence() = sequence {
    println("Add 1")
    yield(1)
    println("Add 2")
    yield(2)
    println("Add 3")
    yield(3)
    println("Add 4")
    yield(4)
    println("Add 5")
    yield(5)
}

fun printSequence(sequence: Sequence<Int>) {
    val iterator = sequence.iterator()
    val i = iterator.next()
    println("Get$i")
    val j = iterator.next()
    println("Get$j")
    val k = iterator.next()
    println("Get$k")
    val l = iterator.next()
    println("Get$l")
    val m = iterator.next()
    println("Get$m")
}

/*
输出结果：
Add 1
Get1
Add 2
Get2
Add 3
Get3
Add 4
Get4
*/