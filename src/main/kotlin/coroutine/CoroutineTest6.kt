import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
fun main() = runBlocking {
    val channel = Channel<Int>()

    launch {
        (1..3).forEach {
            channel.send(it)
            logX("send $it")
        }
        channel.close()
    }

    launch {
        for (x in channel) {
            logX("receive $x")
        }
    }

    logX("Done!")
}*/


// 代码段4

/*fun main() = runBlocking {
    // 变化在这里
    val channel = Channel<Int>(capacity = Channel.Factory.CONFLATED)
    launch {
        (1..3).forEach {
            channel.send(it)
            println("Send: $it")
        }
        channel.close() // 变化在这里
    }
    launch {
        for (i in channel) {
            println("Receive: $i")
        }
    }
    println("end")
}*/

/*fun main() = runBlocking {
    val channel = produce {
        (1..4).forEach {
            send(it)
            println("Send: $it")
        }
    }

    while (!channel.isClosedForReceive) {
        println("Receive: ${channel.receive()}")
    }

    println("end")
}*/



// 代码段13

/*fun main() = runBlocking {
    // 变化在这里
    val channel: ReceiveChannel<Int> = produce(capacity = 3) {
        // 变化在这里
        (1..300).forEach {
            send(it)
            println("Send $it")
        }
    }

    channel.consumeEach {
        println("Receive: $it")
    }

    logX("end")
}*/


// 代码段19

class ChannelModel {
    // 对外只提供读取功能
    val channel: ReceiveChannel<Int> by ::_channel
    private val _channel: Channel<Int> = Channel()

    suspend fun init() {
        (1..3).forEach {
            _channel.send(it)
        }
    }
}

fun main() = runBlocking {
    val model = ChannelModel()
    launch {
        model.init()
    }

    model.channel.consumeEach {
        println(it)
    }
}
