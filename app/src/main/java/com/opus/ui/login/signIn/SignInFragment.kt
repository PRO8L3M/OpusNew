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
import com.opus.common.customs.LoadingResult
import com.opus.data.entity.FirebaseResult
import com.opus.data.entity.UserCredentials
import com.opus.ext.dataBinding
import com.opus.ext.navigateTo
import com.opus.ext.snackBar
import com.opus.ext.toast
import com.opus.mobile.R
import com.opus.mobile.databinding.FragmentSignInBinding
import com.opus.ui.login.LoginViewModel
import com.opus.util.FirebaseObserver
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignInFragment : BaseFragment() {

    private val viewModel: LoginViewModel by sharedViewModel()
    private val binding: FragmentSignInBinding by dataBinding()
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
        binding.progressBtnSignIn.setOnClickListener {
            handleSignInButtonState(false)
            /* todo LEAK while providing password we switch passwordToggle from Invisible to Visible state and navigate to another Fragment, then the leak occurs
                    example: We provided email. Nextly, while providing password, after 2 chars we switched `passwordToggle` to Visible state and provided rest of the password.
                             After that we clicked Button `Sign In` and NavComponent brought us to another Fragment. Leak occurred after few seconds.
                             If we provide entire password within one `passwordToggle` state everything is fine.
            */
            val email = binding.signInEmailInputEditText.text.toString()
            val password = binding.signInPasswordInputEditText.text.toString()
            val userCredentials = UserCredentials(email, password)
            viewModel.signIn(userCredentials)
        }

        binding.signInButtonCreateAccount.setOnClickListener {
            val extras = FragmentNavigatorExtras(it to SHARED_ELEMENT)
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment, null, null, extras)
        }

        binding.signInButtonForgotPassword.setOnClickListener {
            showForgotPasswordAlertDialog()
        }
    }

    private fun handleSignInButtonState(isActive: Boolean) {
        fun switchButtonState(isActive: Boolean) = with(binding.progressBtnSignIn) {
            isActivated = isActive
            isClickable = isActive
        }
        if (isActive) switchButtonState(true) else switchButtonState(false)
    }

    private fun onSuccess(authResult: AuthResult) {
        navigateTo(R.id.action_signInFragment_to_orderFragment)
        handleSignInButtonState(true)
        binding.progressBtnSignIn.deactivateProgressIndicator(LoadingResult.SUCCESS)
    }

    private fun onFailure(exception: Exception) {
        handleSignInButtonState(true)
        snackBar(exception.localizedMessage ?: UNKNOWN_ERROR)
        binding.progressBtnSignIn.deactivateProgressIndicator(LoadingResult.FAILURE)
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
