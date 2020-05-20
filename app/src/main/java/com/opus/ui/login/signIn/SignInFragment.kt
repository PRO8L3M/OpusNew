package com.opus.ui.login.signIn

import android.os.Bundle
import android.transition.Visibility
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.Hold
import com.google.firebase.auth.AuthResult
import com.opus.common.DOUBLE_BACK_PRESSED_DURATION
import com.opus.common.SHARED_ELEMENT
import com.opus.common.UNKNOWN_ERROR
import com.opus.common.customs.CustomAlertDialogFragment
import com.opus.common.customs.progressButton.LoadingResult
import com.opus.data.entity.FirebaseResult
import com.opus.data.entity.UserCredentials
import com.opus.ext.dataBinding
import com.opus.ext.navigateTo
import com.opus.ext.snackBar
import com.opus.ext.toast
import com.opus.mobile.BR
import com.opus.mobile.R
import com.opus.mobile.databinding.FragmentSignInBinding
import com.opus.ui.login.LoginViewModel
import com.opus.util.FirebaseObserver
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val viewModel: LoginViewModel by sharedViewModel()
    private val binding: FragmentSignInBinding by dataBinding()
    private val hold = Hold()
    private var isDoubleClicked = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        assignExitTransition(hold)
        observeAccountLogin()
        observeResetPassword()
        setUpButtonListeners()
        onDoubleBackPressed()
    }

    private fun assignExitTransition(transition: Visibility) { exitTransition = transition }

    private fun observeAccountLogin() = viewModel.accountLogin.observe(
        viewLifecycleOwner,
        FirebaseObserver(::onSuccess, ::onFailure, ::onLoading)
    )

    private fun observeResetPassword() {
        viewModel.resetPassword.observe(viewLifecycleOwner, Observer {
            when (it) {
                is FirebaseResult.Success -> {
                    snackBar(
                        resources.getString(R.string.forgot_password_reset_success)
                    )
                }
                is FirebaseResult.Failure -> {
                    snackBar(it.exception.localizedMessage ?: UNKNOWN_ERROR)
                }
            }
        })
    }

    private fun showForgotPasswordAlertDialog() {
        val alertFragment =
            CustomAlertDialogFragment.newInstance(getString(R.string.forgot_password_alert_message))
        alertFragment.show(parentFragmentManager, FORGOT_PASSWORD_ALERT_TAG)
    }

    private fun setUpButtonListeners() = with(binding) {
        progressBtnSignIn.setOnClickListener {
            /* todo LEAK while providing password we switch passwordToggle from Invisible to Visible state and navigate to another Fragment, then the leak occurs
                    example: We provided email. Nextly, while providing password, after 2 chars we switched `passwordToggle` to Visible state and provided rest of the password.
                             After that we clicked Button `Sign In` and NavComponent brought us to another Fragment. Leak occurred after few seconds.
                             If we provide entire password within one `passwordToggle` state everything is fine.
            */
            val email = signInEmailInputEditText.text.toString()
            val password = signInPasswordInputEditText.text.toString()
            val userCredentials = UserCredentials(email, password)
            viewModel.signIn(userCredentials)
        }

        signInButtonCreateAccount.setOnClickListener {
           navigateWithExtras(it)
        }

        signInButtonForgotPassword.setOnClickListener {
            showForgotPasswordAlertDialog()
        }
    }

    private fun navigateWithExtras(view: View) {
        val extras = FragmentNavigatorExtras(view to SHARED_ELEMENT)
        findNavController().navigate(
            R.id.action_signInFragment_to_signUpFragment,
            null,
            null,
            extras
        )
    }

    private fun onSuccess(authResult: AuthResult) {
        binding.progressBtnSignIn.deactivateProgressIndicator(LoadingResult.SUCCESS)
        navigateTo(R.id.action_signInFragment_to_orderFragment)
    }

    private fun onFailure(exception: Exception) {
        binding.progressBtnSignIn.deactivateProgressIndicator(LoadingResult.FAILURE)
        snackBar(exception.localizedMessage ?: UNKNOWN_ERROR)
    }

    private fun onLoading() {
        binding.progressBtnSignIn.activateLoadingState()
    }

    private fun onDoubleBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (isDoubleClicked) {
                requireActivity().finish()
            }
            isDoubleClicked = true
            toast(resources.getString(R.string.sign_in_exit_app))
            viewLifecycleOwner.lifecycle.coroutineScope.launch {
                delay(DOUBLE_BACK_PRESSED_DURATION)
                isDoubleClicked = false
            }
        }
    }

    companion object {
        const val FORGOT_PASSWORD_ALERT_TAG = "forgot_password_alert_tag"
    }
}
