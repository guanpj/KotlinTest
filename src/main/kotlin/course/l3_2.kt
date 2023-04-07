package course

fun main() {
    getLength("Kotlin", object : Callback<Int> {
        override fun resume(value: Int) {
            println(value)
        }
    })
    Thread.sleep(3000)
}

fun getLength(string: String, callback: Callback<Int>) {
    Thread.sleep(2000)
    callback.resume(string.length)
}

interface Callback<T> {
    fun resume(value: T)
}