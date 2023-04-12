package function

import com.sun.org.apache.xpath.internal.functions.Function2Args
import kotlin.reflect.KFunction

fun main() {
    "a".aaa {
        println(this)
    }
    "b".bbb {
        println(this)
    }
    "c".ccc { s: String, s1: String ->
        println(s)
    }
    "d".ddd {
        println(it)
    }

    (String::aaa)("e") {
        println(this)
    }
    String::aaa.invoke("f") {
        println(this)
    }

    val ggg = String::ddd
}

fun <T> T.aaa(block: T.() -> Unit) {
    this.block()
    block(this)
}

fun <T> T.bbb(block: T.(String) -> Unit) {
    this.block("block b")
    block(this, "block b")
}

fun <T> T.ccc(block: (T, String) -> Unit) {
    block(this, "ccc")
}

fun <T> T.ddd(block: (T) -> Unit) {
    block(this)
}

fun <T> T.eee(block: (T) -> Unit) {
    block(this)
}
