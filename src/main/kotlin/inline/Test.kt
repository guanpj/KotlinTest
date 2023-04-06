package inline

fun main() {
    println("Start")
    sum(1, 2) {
        println("Result is: $it")
        //return // 编译报错，不允许返回
    }
    println("Done")
}

inline fun sum(a: Int, b: Int, crossinline printResult: (result: Int) -> Unit): Int {
    val r = a + b
    printResult(r)
    return r
}


