package kthttp

fun main() {
    val list = listOf<Number>(1, 2.0, 3, 4, 5.5, 6)
    list.filterIsInstance<Double>()?.takeIf { it.isNotEmpty() }?.let { println(it) }?: println("empty")
}