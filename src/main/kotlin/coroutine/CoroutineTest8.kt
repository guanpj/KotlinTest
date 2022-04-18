import kotlinx.coroutines.async
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

// 代码段1
/*fun main() = runBlocking<Unit> {
    suspend fun getCacheInfo(productId: String): Product? {
        delay(2200L)
        return Product(productId, 9.9)
    }

    suspend fun getNetworkInfo(productId: String): Product? {
        delay(200L)
        return Product(productId, 9.8)
    }

    fun updateUI(product: Product) {
        println("${product.productId}==${product.price}")
    }

    val startTime = System.currentTimeMillis()

    val productId = "xxxId"

    val product = select<Product?> {
        async { getCacheInfo(productId) }.onAwait {
            it
        }

        async { getNetworkInfo(productId) }.onAwait {
            it
        }
    }

    if (product != null) {
        updateUI(product)
        println("Time cost: ${System.currentTimeMillis() - startTime}")
    }
}*/


// 代码段5
/*fun main() = runBlocking {
    suspend fun getCacheInfo(productId: String): Product? {
        delay(100L)
        return Product(productId, 9.9)
    }

    suspend fun getNetworkInfo(productId: String): Product? {
        delay(200L)
        return Product(productId, 9.8)
    }

    fun updateUI(product: Product) {
        println("${product.productId}==${product.price}")
    }

    val startTime = System.currentTimeMillis()
    val productId = "xxxId"

    // 1，缓存和网络，并发执行
    val cacheDeferred = async { getCacheInfo(productId) }
    val latestDeferred = async { getNetworkInfo(productId) }

    // 2，在缓存和网络中间，选择最快的结果
    val product = select<Product?> {
        cacheDeferred.onAwait {
            it?.copy(isCache = true)
        }

        latestDeferred.onAwait {
            it?.copy(isCache = false)
        }
    }

    // 3，更新UI
    if (product != null) {
        updateUI(product)
        println("Time cost: ${System.currentTimeMillis() - startTime}")
    }

    // 4，如果当前结果是缓存，那么再取最新的网络服务结果
    if (product != null && product.isCache) {
        val latest = latestDeferred.await()?: return@runBlocking
        updateUI(latest)
        println("Time cost: ${System.currentTimeMillis() - startTime}")
    }
}

data class Product(
    val productId: String,
    val price: Double,
    // 是不是缓存信息
    val isCache: Boolean = false
)*/


// 代码段9
fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val channel1 = produce {
        send("1")
        delay(20000L)
        /*send("2")
        delay(200L)
        send("3")
        delay(150L)*/
    }

    val channel2 = produce {
        delay(100L)
        send("a")
        delay(200L)
        send("b")
        delay(200L)
        send("c")
    }

    suspend fun selectChannel(channel1: ReceiveChannel<String>, channel2: ReceiveChannel<String>): String = select {
        // 1， 选择channel1
        channel1.onReceiveCatching {
            it.getOrNull() ?: "channel is closed"
        }
        // 2， 选择channel1
        channel2.onReceiveCatching {
            it.getOrNull() ?: "channel is closed"
        }
    }

    repeat(5) {// 3， 选择6次结果
        val res = selectChannel(channel1, channel2)
        println(res)
    }

    channel1.cancel()
    channel2.cancel()

    println("Time cost: ${System.currentTimeMillis() - startTime}")
}

