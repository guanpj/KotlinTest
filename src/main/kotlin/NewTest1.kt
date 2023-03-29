import kotlinx.coroutines.*
import java.io.File

fun main() {
    GlobalScope.launch {
        val isSuccess = copyFileTo1(File("old.mp4"), File("new.mp4"))
        println("---copy:$isSuccess")
    }
}

suspend fun copyFileTo1(oldFile: File, newFile: File): Boolean {
    val isCopySuccess = withContext(Dispatchers.IO) {
        try {
            oldFile.copyTo(newFile)
            // 示例代码，通常这里需要验证字节流或者MD5
            true
        } catch (e: Exception) {
            false
        }
    }
    return isCopySuccess
}