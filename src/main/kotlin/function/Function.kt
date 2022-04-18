package com.me.guanpj.kotlin.test.function

fun main() {
    "a".aaa {
        println(this)
    }
    "b".bbb {
        println(this)
    }
    "c".ccc {
        println(it)
    }
}

fun <T> T.aaa(block: T.() -> Unit) {
    this.block()
    block(this)
}

fun <T> T.bbb(block: T.() -> Unit) {
    this.block()
    block(this)
}

fun <T> T.ccc(block: (T) -> Unit) {
    //this.block()
    block(this)
}
