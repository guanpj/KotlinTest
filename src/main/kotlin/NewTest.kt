import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File

fun main() = runBlocking {
    val isSuccess = copyFileTo(File("old.mp4"), File("new.mp4"))
    println("---copy:$isSuccess")
}

suspend fun copyFileTo(oldFile: File, newFile: File): Boolean {
    val isCopySuccess = withContext(Dispatchers.IO) {
        try {
            oldFile.copyTo(newFile)
            true
        } catch (e: Exception) {
            false
        }
    }
    return isCopySuccess
}