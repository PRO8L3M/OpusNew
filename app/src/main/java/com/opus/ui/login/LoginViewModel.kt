package com.opus.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.opus.data.entity.FirebaseResult
import com.opus.data.entity.FirebaseState
import com.opus.data.entity.UserCredentials
import com.opus.data.repository.LoginRepository
import com.opus.util.SingleLiveEvent
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    // todo Have a leak! Something related to firebase/coroutines/editText. Maybe reduce logic to one fragment with tags

    private val _accountCreation: SingleLiveEvent<FirebaseResult<FirebaseState>> by lazy { SingleLiveEvent<FirebaseResult<FirebaseState>>() }
    val accountCreation = _accountCreation

    private val _accountLogin: SingleLiveEvent<FirebaseResult<FirebaseState>> by lazy { SingleLiveEvent<FirebaseResult<FirebaseState>>() }
    val accountLogin = _accountLogin

    fun signIn(userCredentials: UserCredentials) {
        viewModelScope.launch {
            repository.signIn(userCredentials).also { value ->
                _accountLogin.value = value
            }
        }
    }

    fun signUp(userCredentials: UserCredentials) {
        viewModelScope.launch {
            repository.signUp(userCredentials).also { value ->
                _accountCreation.value = value
            }
        }
    }
}