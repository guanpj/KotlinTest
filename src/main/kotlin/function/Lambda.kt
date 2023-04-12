package function

fun a(f: (Int) -> String) {
    println(f(6))
}

fun b(p: Int): String {
    return p.toString()
}

fun main() {
    a(::b)
    a(fun (p: Int): String {
        return p.toString()
    })
    a {
        it.toString()
    }

    val c: (Int) -> String = fun (p: Int): String {
        return p.toString()
    }
    println("c:$c, c(8):${c(8)}")

    val d: (Int) -> String = ::b
    println("d:$d, d(9):${d(9)}")

    val e: (Int) -> String = {
        it.toString()
    }
    println("e:$e, e(10):${e(10)}")
}