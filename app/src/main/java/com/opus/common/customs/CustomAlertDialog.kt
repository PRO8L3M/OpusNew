package com.opus.common.customs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.opus.ext.EMPTY
import com.opus.ext.isEmail
import com.opus.mobile.R
import com.opus.ui.login.LoginViewModel
import com.opus.util.FirebaseObserver
import kotlinx.android.synthetic.main.forgot_password_edit_text.forgot_password_button_negative
import kotlinx.android.synthetic.main.forgot_password_edit_text.forgot_password_button_positive
import kotlinx.android.synthetic.main.forgot_password_edit_text.forgot_password_edit_text_email
import kotlinx.android.synthetic.main.forgot_password_edit_text.forgot_password_input_layout
import kotlinx.android.synthetic.main.forgot_password_edit_text.forgot_password_message
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class CustomAlertDialogFragment : DialogFragment() {

    private lateinit var message: String
    private val viewModel: LoginViewModel by sharedViewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        arguments?.let {
            message = it.getString(MESSAGE_KEY, String.EMPTY)
        }

        viewModel.resetPassword.observe(this, FirebaseObserver(::onSuccess, ::onFailure, ::onLoading))

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

                if (inputLayout.error == null) viewModel.resetPassword(providedEmail)
            }
            it.forgot_password_button_negative.setOnClickListener { alertDialog.dismiss() }
        }
    }

    private fun onSuccess(nothing: Void?) {
        Timber.i("aaa dialog onSuccess")
    }
    private fun onFailure(exception: Exception) {
        Timber.i("aaa dialog onFailure ${exception.localizedMessage}")
    }
    private fun onLoading() {
        Timber.i("aaa dialog onLoading")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.resetPassword.removeObservers(this)
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