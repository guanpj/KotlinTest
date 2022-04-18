package com.me.guanpj.kotlin.test.singleton

class UserManager private constructor(name: String) {
    companion object : BaseSingleton<String, UserManager>() {
        override fun creator(t: String): UserManager = UserManager(t)
    }
}