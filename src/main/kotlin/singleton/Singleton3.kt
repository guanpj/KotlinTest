package com.me.guanpj.kotlin.test.singleton

class Singleton3 private constructor() {
    companion object {
        @JvmStatic
        val instance by lazy { Singleton3() }
    }
}