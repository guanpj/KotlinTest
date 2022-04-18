package com.me.guanpj.kotlin.test.singleton

abstract class BaseSingleton1<in P, out T> {
    private var instance: T? = null

    protected abstract val creator: (P) -> T

    fun getInstance(p: P): T =
        instance ?: synchronized(this) {
            instance ?: creator(p).also { instance = it }
        }
}