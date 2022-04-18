package com.me.guanpj.kotlin.test.function

import kotlin.reflect.*

class TestClass {
    var testVar: String = "test var in class"

    fun testMethod(par: Int) : String {
        return "test method :$par"
    }
}

fun testFunction(par: Int) : String {
    return "test fun :$par"
}

fun main() {
    val testClass = TestClass()
    testClass.testVar = "modify 1"
    println(testClass.testVar)

    testClass::testVar.set("modify 2")
    println(testClass::testVar.get())

    TestClass::testVar.set(testClass, "modify 3")
    println(TestClass::testVar.get(testClass))

    val funTest1: (Int) -> String = ::testFunction
    val funTest2: Int.() -> String = ::testFunction
    val funTest3: KFunction1<Int, String> = ::testFunction

    val methodTest1: TestClass.(Int) -> String = TestClass::testMethod
    val methodTest2: (TestClass, Int) -> String = TestClass::testMethod
    val methodTest3: KFunction2<TestClass, Int, String> = TestClass::testMethod

    println(funTest1(1))
    println(funTest2(2))
    println(funTest3(3))

    println(methodTest1(testClass, 4))
    println(methodTest2(testClass, 5))
    println(methodTest3(testClass, 6))

    val funTest4 = fun(par: Int) : String = "abc$par"
    println(funTest4.invoke(444))
    val funTest5 : (Int) -> String = fun(par) : String = "abc$par"
    println(funTest5.invoke(555))
    val funTest6 : (Int) -> String = {
        "abc$it"
    }
    println(funTest6.invoke(666))

    /*val funTest7 : (Int) -> String = object: KFunction1<Int, String> {
        override val annotations: List<Annotation>
            get() = emptyList()
        override val isAbstract: Boolean
            get() = false
        override val isFinal: Boolean
            get() = false
        override val isOpen: Boolean
            get() = false
        override val name: String
            get() = "abc"
        override val parameters: List<KParameter>
            get() = emptyList()
        override val returnType: KType
            get() = object : KType {
                override val annotations: List<Annotation>
                    get() = emptyList()
                override val arguments: List<KTypeProjection>
                    get() = emptyList()
                override val classifier: KClassifier?
                    get() = null
                override val isMarkedNullable: Boolean
                    get() = false
            }
        override val typeParameters: List<KTypeParameter>
            get() = emptyList()
        override val visibility: KVisibility?
            get() = KVisibility.PUBLIC

        override fun call(vararg args: Any?): String {
            return "a"
        }

        override fun callBy(args: Map<KParameter, Any?>): String {
            return "a"
        }

        override val isExternal: Boolean
            get() = false
        override val isInfix: Boolean
            get() = false
        override val isInline: Boolean
            get() = false
        override val isOperator: Boolean
            get() = false
        override val isSuspend: Boolean
            get() = false

        override fun invoke(p1: Int): String {
            return "abc$p1"
        }
    }

    println(funTest7.invoke(777))*/

    /*val funTest8 : (Int) -> String = object : KFunction1<Int, String> {
        override val name: String
            get() = "funTest9"

        override fun invoke(p1: Int): String {
            return "abc$p1"
        }
    }
    println(funTest8(888))*/
}