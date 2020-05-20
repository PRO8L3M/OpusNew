package com.opus.data.dataSource.remote

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.opus.data.entity.UserCredentials
import kotlinx.coroutines.tasks.await

class FirebaseImpl(private val firebaseAuth: FirebaseAuth) {

    suspend fun signUp(userCredentials: UserCredentials): AuthResult =
        firebaseAuth.createUserWithEmailAndPassword(
            userCredentials.email,
            userCredentials.password
        )
            .addOnCompleteListener {
                if (it.isSuccessful) firebaseAuth.currentUser?.sendEmailVerification()
            }.await()

    suspend fun signIn(userCredentials: UserCredentials): AuthResult =
        firebaseAuth.signInWithEmailAndPassword(userCredentials.email, userCredentials.password)
            .await()

    suspend fun resetPassword(email: String) = firebaseAuth.sendPasswordResetEmail(email).await()

}
