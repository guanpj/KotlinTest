package inline

fun main() {
    printName("guanpj",
        {
            println("hello!")
        },
        { println("bye!") }
    )
}

inline fun printName(name: String, crossinline preAction: () -> Unit, postAction: () -> Unit) {
    preAction()
    println("your name is: $name")
    postAction()
}