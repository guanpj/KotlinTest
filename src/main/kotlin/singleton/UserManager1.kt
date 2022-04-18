package com.me.guanpj.kotlin.test.singleton

class UserManager1 private constructor(name: String) {
    companion object : BaseSingleton1<String, UserManager1>() {
        override val creator = ::UserManager1
    }
}