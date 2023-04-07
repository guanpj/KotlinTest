package inline

fun main() {
    printName2("guanpj",
        { println("hello!") },
        { println("bye!") }
    )
}

inline fun printName2(name: String, preAction: () -> Unit, crossinline postAction: () -> Unit) {
    preAction()
    println("your name is: $name")
    runOnUiThread {
        postAction()
    }
}

fun runOnUiThread(runner: () -> Unit) {
    println("Now in UI thread!")
    runner()
}