package com.opus.ui.newOrder

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.transition.MaterialContainerTransform
import com.opus.mobile.R
import com.opus.ui.newOrder.voiceDialogFragment.VoiceDialogFragment
import com.opus.ui.newOrder.voiceDialogFragment.VoiceDialogFragment.Companion.VOICE_DIALOG_TAG
import com.opus.util.voiceRecognition.VoiceRecognition.Companion.REQUEST_RECORD_AUDIO_PERMISSION
import kotlinx.android.synthetic.main.fragment_new_order.new_order_start
import kotlinx.android.synthetic.main.fragment_new_order.new_order_stop
import kotlinx.android.synthetic.main.fragment_new_order.new_order_voice_result
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewOrderFragment : Fragment(R.layout.fragment_new_order) {

    private val viewModel: NewOrderViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.voice.observe(viewLifecycleOwner, Observer { setupUi(it) })
        sharedElementEnterTransition = MaterialContainerTransform()
        setUpButtonListeners()
    }

    private fun setUpButtonListeners() {
        new_order_start.setOnClickListener { checkPermissions() }
        new_order_stop.setOnClickListener {
            viewModel.stopListening()
        }
    }

    private fun setupUi(voiceText: String) {
        new_order_voice_result.setText(voiceText)
    }

    private fun showVoiceDialog() {
        val voiceDialogFragment = VoiceDialogFragment.newInstance()
        voiceDialogFragment.show(parentFragmentManager, VOICE_DIALOG_TAG)
    }

    private fun checkPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.RECORD_AUDIO
        )

        fun makeRequest() = requestPermissions(
            arrayOf(Manifest.permission.RECORD_AUDIO),
            REQUEST_RECORD_AUDIO_PERMISSION
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            /** no permission - proceed **/
            if (shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)) {
                /** no permission, once denied and more info required now **/
                showVoiceDialog()
            } else {
                /** no permission, permission grant request **/
                makeRequest()
            }
        } else {
            /** permission already granted **/
            viewModel.startListening()
        }
    }

    override fun onStop() {
        super.onStop()

        viewModel.cancel()
    }
}
