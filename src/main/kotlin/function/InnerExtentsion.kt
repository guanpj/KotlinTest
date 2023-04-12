package function

class Extension {
    fun String.innerExt() {
        println("inner ext")
    }

    fun a() {
        "a".innerExt()
    }
}

fun main() {
    val extension = Extension()

    //Extension.innerExt("aaa")
    //extension::innerExt("aaa")
}
