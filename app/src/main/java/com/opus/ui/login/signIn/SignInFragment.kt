package com.opus.ui.login.signIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.Hold
import com.google.firebase.auth.AuthResult
import com.opus.common.BaseFragment
import com.opus.common.DOUBLE_BACK_PRESSED_DURATION
import com.opus.common.SHARED_ELEMENT
import com.opus.common.UNKNOWN_ERROR
import com.opus.common.customs.CustomAlertDialogFragment
import com.opus.data.entity.FirebaseResult
import com.opus.data.entity.UserCredentials
import com.opus.ext.navigateTo
import com.opus.ext.snackBar
import com.opus.ext.toast
import com.opus.mobile.R
import com.opus.ui.login.LoginViewModel
import com.opus.util.FirebaseObserver
import kotlinx.android.synthetic.main.fragment_sign_in.sign_in_button
import kotlinx.android.synthetic.main.fragment_sign_in.sign_in_button_create_account
import kotlinx.android.synthetic.main.fragment_sign_in.sign_in_button_forgot_password
import kotlinx.android.synthetic.main.fragment_sign_in.sign_in_email_input_edit_text
import kotlinx.android.synthetic.main.fragment_sign_in.sign_in_password_input_edit_text
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class SignInFragment : BaseFragment() {

    private val viewModel: LoginViewModel by sharedViewModel()
    private val hold = Hold()
    private var isDoubleClicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exitTransition = hold

        observeAccountLogin()
        observeResetPassword()

        setUpButtonListeners()

        onDoubleBackPressed()
    }

    private fun observeAccountLogin() = viewModel.accountLogin.observe(viewLifecycleOwner, FirebaseObserver(::onSuccess, ::onFailure, ::onLoading))

    private fun observeResetPassword() {
        viewModel.resetPassword.observe(viewLifecycleOwner, Observer {
            when (it) {
                is FirebaseResult.Success -> { snackBar(resources.getString(R.string.forgot_password_reset_success), Snackbar.LENGTH_LONG) }
                is FirebaseResult.Failure -> { snackBar(it.exception.localizedMessage ?: UNKNOWN_ERROR, Snackbar.LENGTH_LONG) }
            }
        })
    }

    private fun showForgotPasswordAlertDialog() {
        val alertFragment = CustomAlertDialogFragment.newInstance(getString(R.string.forgot_password_alert_message))
        alertFragment.show(parentFragmentManager, FORGOT_PASSWORD_ALERT_TAG)
    }

    private fun setUpButtonListeners() {
        sign_in_button.setOnClickListener {
            handleSignInButtonState(false)
            /* todo LEAK while providing password we switch passwordToggle from Invisible to Visible state and navigate to another Fragment, then the leak occurs
                    example: We provided email. Nextly, while providing password, after 2 chars we switched `passwordToggle` to Visible state and provided rest of the password.
                             After that we clicked Button `Sign In` and NavComponent brought us to another Fragment. Leak occurred after few seconds.
                             If we provide entire password within one `passwordToggle` state everything is fine.
            */
            val email = sign_in_email_input_edit_text.text.toString()
            val password = sign_in_password_input_edit_text.text.toString()
            val userCredentials = UserCredentials(email, password)
            viewModel.signIn(userCredentials)
        }

        sign_in_button_create_account.setOnClickListener {
            val extras = FragmentNavigatorExtras(it to SHARED_ELEMENT)
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment, null, null, extras)
        }

        sign_in_button_forgot_password.setOnClickListener {
            showForgotPasswordAlertDialog()
        }
    }

    private fun handleSignInButtonState(isActive: Boolean) {
        fun switchButtonState(isActive: Boolean) = with(sign_in_button) {
            isActivated = isActive
            isClickable = isActive
        }
        if (isActive) switchButtonState(true) else switchButtonState(false)
    }

    private fun onSuccess(authResult: AuthResult) {
        handleSignInButtonState(true)
        navigateTo(R.id.action_signInFragment_to_orderFragment)
    }

    private fun onFailure(exception: Exception) {
        handleSignInButtonState(true)
        snackBar(exception.localizedMessage ?: UNKNOWN_ERROR)
    }

    private fun onLoading() {
        Timber.i("aaa onLoading")
    }

    private fun onDoubleBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (isDoubleClicked) {
                requireActivity().finish()
            }
            isDoubleClicked = true
            toast(resources.getString(R.string.sign_in_exit_app))
            viewScope.launch {
                delay(DOUBLE_BACK_PRESSED_DURATION)
                isDoubleClicked = false
            }
        }
    }

    companion object {
        const val FORGOT_PASSWORD_ALERT_TAG = "forgot_password_alert_tag"
    }
}
