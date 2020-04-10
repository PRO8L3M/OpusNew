package com.opus.data

import com.google.firebase.auth.FirebaseAuth
import com.opus.data.entity.FirebaseState
import com.opus.data.entity.UserCredentials
import com.opus.ext.EMPTY
import kotlinx.coroutines.tasks.await

class FirebaseImpl(private val firebaseAuth: FirebaseAuth) {

    // todo [PN] need refactor to handle exception/messages by resources.strings (future: translations)

    suspend fun signUp(userCredentials: UserCredentials): FirebaseState {
        var resultMessage = String.EMPTY
        val authResult = firebaseAuth.createUserWithEmailAndPassword(
            userCredentials.email,
            userCredentials.password
        )
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseAuth.currentUser?.sendEmailVerification()
                    resultMessage = "Account created successfully. Please check you email and verify account."
                }
            }.await()
        return FirebaseState(authResult, resultMessage)
    }

    suspend fun signIn(userCredentials: UserCredentials): FirebaseState {
        var message = String.EMPTY
        val authResult =
            firebaseAuth.signInWithEmailAndPassword(userCredentials.email, userCredentials.password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) message = "Welcome, ${firebaseAuth.currentUser?.email}"
                }
                .await()
        return FirebaseState(authResult, message)
    }
}