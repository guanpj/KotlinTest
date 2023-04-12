package function

fun Int.ext(p: String) {
    println("$this.ext p: $p")
}

fun function(p1: Int, p2: String) {
    println("function p1: $p1, p2: $p2")
}

fun main() {
    6.ext("6")

    val extVal1: Int.(String) -> Unit = Int::ext
    1.extVal1("1")

    val extVal2: (Int, String) -> Unit = Int::ext
    //2.extVal1("2")

/////////////////////////////////////////////////////////

    function(6, "kotlin")

    //6.function("kotlin")

    val f = ::function
    //4.f("java")
    f(4, "java")

    val f1: Int.(String) -> Unit = ::function
    4.f1("java")
}
