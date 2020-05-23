package com.opus.ui.newOrder.voiceDialogFragment

import android.Manifest
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.opus.mobile.R
import com.opus.util.voiceRecognition.VoiceRecognition.Companion.REQUEST_RECORD_AUDIO_PERMISSION
import kotlinx.android.synthetic.main.dialog_fragment_voice.dialog_button_ok

class VoiceDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setView(R.layout.dialog_fragment_voice)
            .create()
    }

    override fun onResume() {
        super.onResume()

        setUpButtonListeners()
    }

    private fun setUpButtonListeners() {
        (dialog as AlertDialog).dialog_button_ok.setOnClickListener {
            dismiss()
            requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_RECORD_AUDIO_PERMISSION)
        }
    }

    companion object {
        const val VOICE_DIALOG_TAG = "voice"
        fun newInstance() = VoiceDialogFragment()
    }
}