package course

import kotlinx.coroutines.delay
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine

val abc: suspend (String) -> String = {
    print(it)
    delay(2000)
    it
}

val def: suspend String.() -> String = {
    print(this)
    delay(2000)
    this
}

fun main() {
    def.startCoroutine("123", object : Continuation<String> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<String>) {
            println(result.getOrNull())
        }
    })
}