package com.opus.util.voiceRecognition

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import timber.log.Timber

class VoiceRecognition(application: Application) : RecognitionListener {

    val recognition: SpeechRecognizer =
        SpeechRecognizer.createSpeechRecognizer(application).apply { setRecognitionListener(this@VoiceRecognition) }
    val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).putExtra(
        RecognizerIntent.EXTRA_LANGUAGE, Language.POLISH.short
    )

    var resultCallback: ((List<String>) -> Unit) = {}

        override fun onReadyForSpeech(p0: Bundle?) {

            Timber.i("onReadyForSpeech")
        }

        override fun onRmsChanged(p0: Float) {
            Timber.i("onRmsChanged")
        }

        override fun onBufferReceived(p0: ByteArray?) {
            Timber.i("onBufferReceived")
        }

        override fun onPartialResults(p0: Bundle?) {
            Timber.i("onPartialResults")
        }

        override fun onEvent(p0: Int, p1: Bundle?) {
            Timber.i("onEvent")
        }

        override fun onBeginningOfSpeech() {
            Timber.i("onBeginningOfSpeech")
        }

        override fun onEndOfSpeech() {
            Timber.i("onEndOfSpeech")
        }

        override fun onError(error: Int) {
            when (error) {
                SpeechRecognizer.ERROR_AUDIO -> Timber.i("audio")
                SpeechRecognizer.ERROR_CLIENT -> Timber.i("client")
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> Timber.i("insufficient permissions")
                SpeechRecognizer.ERROR_NETWORK -> Timber.i("network")
                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> Timber.i("network timeout")
                SpeechRecognizer.ERROR_NO_MATCH -> Timber.i("no match")
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> Timber.i("busy")
                SpeechRecognizer.ERROR_SERVER -> Timber.i("server")
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> Timber.i("timeout")
            }
        }

        override fun onResults(bundle: Bundle?) {
            val result =
                bundle?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.toList()
                    ?: emptyList()
            resultCallback.invoke(result)
            Timber.i("onResults")
        }

    companion object {
        const val REQUEST_RECORD_AUDIO_PERMISSION = 200
    }
}

enum class Language(val short: String) {
    POLISH(short = "pl-PL")
}