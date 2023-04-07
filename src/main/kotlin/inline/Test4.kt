package inline

fun main() {
    val postAction = printName1("guanpj",
        { println("hello!") },
        { println("bye!") }
    )
    postAction()
}

inline fun printName1(name: String, preAction: () -> Unit, noinline postAction: () -> Unit): () -> Unit {
    preAction()
    println("your name is: $name")
    return postAction
}