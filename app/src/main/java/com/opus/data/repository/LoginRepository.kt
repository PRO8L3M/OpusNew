package com.opus.data.repository

import com.opus.data.FirebaseImpl
import com.opus.data.entity.UserCredentials
import com.opus.data.entity.checkIsEmailVerified
import com.opus.data.entity.safeCall


class LoginRepository(private val firebaseImpl: FirebaseImpl) {

    suspend fun signIn(userCredentials: UserCredentials) = safeCall { firebaseImpl.signIn(userCredentials).checkIsEmailVerified() }

    suspend fun signUp(userCredentials: UserCredentials) = safeCall { firebaseImpl.signUp(userCredentials) }
}