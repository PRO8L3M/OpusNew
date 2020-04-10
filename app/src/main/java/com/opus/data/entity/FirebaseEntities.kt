package com.opus.data.entity

import com.google.firebase.auth.AuthResult

data class FirebaseState(val authResult: AuthResult, val resultMessage: String)

data class UserCredentials(val email: String, val password: String)