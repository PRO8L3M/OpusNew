package com.opus.data.entity

import com.google.firebase.auth.AuthResult
import java.lang.Exception

sealed class FirebaseResult <out T> {
    data class Success<out T>(val data: T) : FirebaseResult<T>()
    data class Failure(val exception: Exception) : FirebaseResult<Nothing>()
}

inline fun <T> safeCall(call: (() -> T)): FirebaseResult<T> =
    try {
        FirebaseResult.Success(call())
    } catch (exception: Exception) {
        FirebaseResult.Failure(exception)
    }

fun AuthResult.checkIsEmailVerified(): AuthResult = if (user?.isEmailVerified == true) this else throw Exception("Email is not verified")