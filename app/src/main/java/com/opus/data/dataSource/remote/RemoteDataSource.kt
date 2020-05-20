package com.opus.data.dataSource.remote

import com.opus.data.entity.UserCredentials

class RemoteDataSource(private val firebaseImpl: FirebaseImpl) {
    suspend fun signIn(userCredentials: UserCredentials) = firebaseImpl.signIn(userCredentials)
    suspend fun signUp(userCredentials: UserCredentials) = firebaseImpl.signUp(userCredentials)
    suspend fun resetPassword(email: String) = firebaseImpl.resetPassword(email)
}