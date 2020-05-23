package com.opus.ui.newOrder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.opus.util.voiceRecognition.VoiceRecognition

class NewOrderViewModel(private val voiceRecognition: VoiceRecognition) : ViewModel() {

    private val _voice: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val voice: LiveData<String> = _voice

    fun startListening() = voiceRecognition.recognition.startListening(voiceRecognition.recognizerIntent)
    fun stopListening() {
        voiceRecognition.recognition.stopListening()
        fetchVoiceResult()
    }
    fun cancel() = voiceRecognition.recognition.cancel()

    private fun fetchVoiceResult() {
        voiceRecognition.resultCallback = { list ->
            _voice.value = list.first()
        }
    }
}