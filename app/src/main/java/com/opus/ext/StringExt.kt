package com.opus.ext

import android.util.Patterns

val String.Companion.EMPTY
    get() = ""

fun String.isEmail() : Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()