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
import com.opus.util.argument
import kotlinx.android.synthetic.main.forgot_password_edit_text.forgot_password_button_negative
import kotlinx.android.synthetic.main.forgot_password_edit_text.forgot_password_button_positive
import kotlinx.android.synthetic.main.forgot_password_edit_text.forgot_password_edit_text_email
import kotlinx.android.synthetic.main.forgot_password_edit_text.forgot_password_input_layout
import kotlinx.android.synthetic.main.forgot_password_edit_text.forgot_password_message
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CustomAlertDialogFragment : DialogFragment() {

    private var message: String by argument()
    private val viewModel: LoginViewModel by sharedViewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return MaterialAlertDialogBuilder(requireContext())
            .setView(R.layout.forgot_password_edit_text)
            .create()
    }

    override fun onResume() {
        super.onResume()

        (dialog as? AlertDialog)?.let { alertDialog ->
            with(alertDialog) {
                forgot_password_message.text = message
                forgot_password_button_positive.setOnClickListener {
                    handlePositiveButtonClick(
                        this
                    )
                }
                forgot_password_button_negative.setOnClickListener {
                    handleNegativeButtonClick(
                        this
                    )
                }
            }
        }
    }

    private fun handlePositiveButtonClick(alertDialog: AlertDialog) {
        with(alertDialog) {
            val providedEmail = forgot_password_edit_text_email.text.toString()
            val inputLayout = forgot_password_input_layout

            when {
                providedEmail.isBlank() -> inputLayout.error =
                    resources.getString(R.string.forgot_password_error_empty_field)
                !providedEmail.isEmail() -> inputLayout.error =
                    resources.getString(R.string.forgot_password_error_wrong_pattern)
                else -> inputLayout.error = null
            }

            if (inputLayout.error == null) {
                viewModel.resetPassword(providedEmail)
                dismiss()
            }
        }
    }

    private fun handleNegativeButtonClick(alertDialog: AlertDialog) = alertDialog.dismiss()

    companion object {
        fun newInstance(message: String) = CustomAlertDialogFragment().apply {
            this.message = message
        }
    }
}