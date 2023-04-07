package inline

fun main() {
    println("Start")
    sum(1, 2,
        { println("Result is: $it") },
        { println("Deal result: $it") }
    )
    println("Done")
}

inline fun sum(a: Int, b: Int, printResult: (result: Int) -> Unit, dealResult: (result: Int) -> Unit): Int {
    val r = a + b
    printResult(r)
    abc {
        println(it)
        return it
    }
    return r
}

inline fun abc(printResult: (result: Int) -> Unit) {

}