import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() = runBlocking {
    val flow = flow {
        (1..5).forEach {
            emit(it)
            logX("Emitting $it")
        }
    }
    withContext(mySingleDispatcher) {
        flow.flowOn(Dispatchers.IO)
            .filter {
                logX("Filtering $it")
                it % 2 == 0
            }
            .flowOn(Dispatchers.Default)
            .map {
                it * 2
                logX("Mapping $it")
            }
            .collect { logX("collect $it") }
    }
}