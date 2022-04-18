package com.me.guanpj.kotlin.test.singleton

class Singleton2 private constructor(p: String) {
    companion object {
        @Volatile private var instance: Singleton2? = null
        fun getInstance(p: String): Singleton2 = instance ?: synchronized(this) {
            instance ?: Singleton2(p).also { instance = it }
        }
    }
}