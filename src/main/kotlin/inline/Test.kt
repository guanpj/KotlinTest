package inline

fun main() {
    println("Start")
    val sum = sum(1, 2)
    println("Done: $sum")
}

private inline fun sum(a: Int, b: Int): Int {
    val r = a + b
    return r
}


