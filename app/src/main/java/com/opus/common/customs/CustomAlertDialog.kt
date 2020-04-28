package com.opus.common.customs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.opus.ext.EMPTY
import com.opus.ext.isEmail
import com.opus.mobile.R
import com.opus.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.forgot_password_edit_text.forgot_password_button_negative
import kotlinx.android.synthetic.main.forgot_password_edit_text.forgot_password_button_positive
import kotlinx.android.synthetic.main.forgot_password_edit_text.forgot_password_edit_text_email
import kotlinx.android.synthetic.main.forgot_password_edit_text.forgot_password_input_layout
import kotlinx.android.synthetic.main.forgot_password_edit_text.forgot_password_message
import kotlinx.android.synthetic.main.forgot_password_edit_text.forgot_password_progress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import java.lang.Exception

class CustomAlertDialogFragment : DialogFragment() {

    private lateinit var message: String
    private val viewModel: LoginViewModel by sharedViewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        arguments?.let {
            message = it.getString(MESSAGE_KEY, String.EMPTY)
        }

        return MaterialAlertDialogBuilder(requireContext())
            .setView(R.layout.forgot_password_edit_text)
            .create()
    }

    override fun onResume() {
        super.onResume()

        val alertDialog = dialog as AlertDialog?

        alertDialog?.let {

            it.forgot_password_message.text = message
            it.forgot_password_button_positive.setOnClickListener {

                val providedEmail = alertDialog.forgot_password_edit_text_email.text.toString()
                val inputLayout = alertDialog.forgot_password_input_layout

                when {
                    providedEmail.isBlank() -> inputLayout.error = "Field cannot be empty"
                    !providedEmail.isEmail() -> inputLayout.error = "Wrong email pattern"
                    else -> inputLayout.error = null
                }

                if (inputLayout.error == null) {
                    activity?.lifecycleScope?.launch(Dispatchers.Main) {
                        try {
                            alertDialog.forgot_password_progress.show()
                            delay(2000)
                            viewModel.resetPassword(providedEmail)
                            alertDialog.dismiss()
                        } catch (e: Exception) {
                            Timber.e("aaa blad: $e")
                        }
                    }
                }
            }
            it.forgot_password_button_negative.setOnClickListener { alertDialog.dismiss() }
        }
    }

    companion object {
        private const val MESSAGE_KEY = "message_key"

        fun newInstance(message: String) = CustomAlertDialogFragment().apply {
            val args = Bundle().apply {
                putString(MESSAGE_KEY, message)
            }
            arguments = args
        }
    }
}