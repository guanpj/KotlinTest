package com.me.guanpj.kotlin.test

class KotlinView {
    var mOnClickListener: ((KotlinView) -> Unit)? = null

    fun setOnClickListener(l: (KotlinView) -> Unit) {
        mOnClickListener = l
    }

    fun click() {
        println("KotlinView On Click")
        mOnClickListener?.invoke(this)
    }

    var mOnLongClickListener: OnLongClickListener? = null

    fun interface OnLongClickListener {
        fun onLongClick(v: KotlinView)
    }

    fun setOnLongClickListener(l: OnLongClickListener) {
        mOnLongClickListener = l
    }

    fun longClick() {
        println("KotlinView On Long Click")
        mOnLongClickListener?.onLongClick(this)
    }
}