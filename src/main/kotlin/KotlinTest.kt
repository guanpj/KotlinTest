import com.me.guanpj.kotlin.test.KotlinView

fun main() {
    val kotlinView = KotlinView()
    kotlinView.setOnClickListener {
        println("After KotlinView On Click")
    }
    kotlinView.setOnClickListener(object: (KotlinView) -> Unit {
        override fun invoke(p1: KotlinView) {
            println("After KotlinView On Click")
        }
    })
    kotlinView.click()

    kotlinView.setOnLongClickListener(object : KotlinView.OnLongClickListener{
        override fun onLongClick(v: KotlinView) {
        }
    })
    kotlinView.setOnLongClickListener(KotlinView.OnLongClickListener { v: KotlinView? ->

    })

    kotlinView.setOnLongClickListener {

    }
    kotlinView.longClick()

    val javaView = JavaView()
    javaView.setOnClickListener(object : JavaView.OnClickListener {
        override fun onClick(v: JavaView?) {
            TODO("Not yet implemented")
        }
    })

    javaView.setOnClickListener(JavaView.OnClickListener { v: JavaView? ->

    })

    javaView.setOnClickListener({ v: JavaView? ->

    })

    javaView.setOnClickListener({ v ->

    })

    javaView.setOnClickListener({ it ->

    })

    javaView.setOnClickListener({

    })

    javaView.setOnClickListener() {

    }

    javaView.setOnClickListener {

    }
}