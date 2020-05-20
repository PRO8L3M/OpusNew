package com.opus.repository

import com.opus.data.dataSource.local.LocalDataSource
import com.opus.data.dataSource.remote.RemoteDataSource
import com.opus.data.entity.UserCredentials
import com.opus.data.entity.checkIsEmailVerified
import com.opus.data.entity.safeCall

class LoginRepositoryImpl(private val local: LocalDataSource, private val remote: RemoteDataSource): ILoginRepository  {

    override suspend fun signUp(userCredentials: UserCredentials) = safeCall { remote.signUp(userCredentials) }

    override suspend fun signIn(userCredentials: UserCredentials) = safeCall { remote.signIn(userCredentials).checkIsEmailVerified() }

    override suspend fun resetPassword(email: String) = safeCall { remote.resetPassword(email) }
}