package com.me.guanpj.kotlin.test.coroutine

fun main() {
    val seq = getSeq()
    printSeq(seq)
}

fun getSeq() = sequence {
    println("add1")
    yield(1)

    println("add2")
    yield(2)

    println("add3")
    yield(3)
}

fun printSeq(seq: Sequence<Int>) {
    val iterator = seq.iterator()
    println("Get${iterator.next()}")
    println("Get${iterator.next()}")
    println("Get${iterator.next()}")
}

