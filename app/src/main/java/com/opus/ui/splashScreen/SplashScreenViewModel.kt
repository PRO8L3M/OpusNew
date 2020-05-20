package com.opus.ui.splashScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.opus.common.SPLASH_SCREEN_DURATION
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenViewModel : ViewModel() {

    private val _isReadyToNavigate: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>().apply { value = false } }
    val isReadyToNavigate: LiveData<Boolean> = _isReadyToNavigate

    fun startSplashScreen() {
        viewModelScope.launch {
            delay(SPLASH_SCREEN_DURATION)
            _isReadyToNavigate.value = true
        }
    }
}