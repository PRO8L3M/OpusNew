package com.opus.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.opus.data.entity.FirebaseResult
import com.opus.data.entity.UserCredentials
import com.opus.data.repository.LoginRepository
import com.opus.ext.isEmail
import com.opus.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    private val _accountCreation: SingleLiveEvent<FirebaseResult<AuthResult>> by lazy { SingleLiveEvent<FirebaseResult<AuthResult>>() }
    val accountCreation = _accountCreation

    private val _accountLogin: SingleLiveEvent<FirebaseResult<AuthResult>> by lazy { SingleLiveEvent<FirebaseResult<AuthResult>>() }
    val accountLogin = _accountLogin

    fun signIn(userCredentials: UserCredentials) {
        viewModelScope.launch{
            val result = withContext(Dispatchers.IO) { repository.signIn(userCredentials) }
            _accountLogin.value = result
        }
    }

    fun signUp(userCredentials: UserCredentials) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { repository.signUp(userCredentials) }
            _accountCreation.value = result
        }
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (email.isEmail()) repository.resetPassword(email)

            }
        }
    }
}