package com.opus.repository

import com.google.firebase.auth.AuthResult
import com.opus.data.entity.FirebaseResult
import com.opus.data.entity.UserCredentials

interface ILoginRepository {
    suspend fun signUp(userCredentials: UserCredentials): FirebaseResult<AuthResult>
    suspend fun signIn(userCredentials: UserCredentials): FirebaseResult<AuthResult>
    suspend fun resetPassword(email: String): FirebaseResult<Void>
}