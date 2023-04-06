package kthttp

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.lang.reflect.ParameterizedType
import kotlin.coroutines.Continuation
import kotlin.reflect.jvm.javaMethod

fun main() = runBlocking {
    /*val method = ::foo.javaMethod
    val returnType = method?.genericReturnType
    println(returnType) // 输出：class kotlin.String

    val methodSus = ::bar.javaMethod
    val returnTypeSus = methodSus?.returnType
    println(returnTypeSus) // 输出：interface kotlin.coroutines.Continuation

    val kFunction = ::foo
    val returnTypeK = kFunction.returnType
    println(returnTypeK) // 输出：kotlin.String

    val kFunctionSus = ::bar
    val returnTypeKSus = kFunctionSus.returnType
    print(returnTypeKSus)*/

    val method = ::bar.javaMethod
    val returnType = method?.genericReturnType
    val actualReturnType = if (returnType is ParameterizedType && returnType.rawType == Int::class.java) {
        // 挂起函数
        val suspendFunction = ::bar::class.java.getDeclaredMethod("bar", Continuation::class.java)
        kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn<Int> {
            //suspendFunction.invoke(::bar, it)
        }.javaClass.kotlin.javaObjectType
    } else {
        // 普通函数
        returnType
    }
    println(actualReturnType) // 输出：class kotlin.String
}

fun foo(): String {
    return "Hello World"
}

suspend fun bar(): Int {
    delay(1000)
    return 42
}