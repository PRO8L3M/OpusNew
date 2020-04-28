package com.opus.ui.login.signIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.Hold
import com.google.firebase.auth.AuthResult
import com.opus.common.BaseFragment
import com.opus.common.customs.CustomAlertDialogFragment
import com.opus.data.entity.UserCredentials
import com.opus.ext.navigateTo
import com.opus.ext.snackBar
import com.opus.mobile.R
import com.opus.ui.login.LoginViewModel
import com.opus.util.FirebaseObserver
import kotlinx.android.synthetic.main.fragment_sign_in.sign_in_button
import kotlinx.android.synthetic.main.fragment_sign_in.sign_in_button_create_account
import kotlinx.android.synthetic.main.fragment_sign_in.sign_in_button_forgot_password
import kotlinx.android.synthetic.main.fragment_sign_in.sign_in_email_input_edit_text
import kotlinx.android.synthetic.main.fragment_sign_in.sign_in_password_input_edit_text
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignInFragment : BaseFragment() {

    private val viewModel: LoginViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exitTransition = Hold()

        viewModel.accountLogin.observe(
            viewLifecycleOwner,
            FirebaseObserver(::onSuccess, ::onFailure)
        )

        setUpButtonsListeners()

       /* fab.setOnClickListener {

            makeTransition(fab, black_square, login_layout)

            fab.visibility = View.GONE
            black_square.visibility = View.VISIBLE
        }

        black_square.setOnClickListener {
            makeTransition(black_square, fab, login_layout)

            black_square.visibility = View.GONE
            fab.visibility = View.VISIBLE
        }*/
    }

/*    private fun makeTransition(
        startView: View,
        endView: View,
        rootView: ViewGroup,
        scrimColor: Int = Color.TRANSPARENT,
        pathMotion: PathMotion = MaterialArcMotion()
    ) {
        val transform = MaterialContainerTransform().apply {
            this.startView = startView
            this.endView = endView
            this.pathMotion = pathMotion
            this.scrimColor = scrimColor
        }
        TransitionManager.beginDelayedTransition(rootView, transform)
    }*/


    private fun showLoginState(text: String) {
        snackBar(text, duration = Snackbar.LENGTH_LONG)
    }

    private fun showForgotPasswordAlertDialog() {
        val alertFragment = CustomAlertDialogFragment.newInstance(getString(R.string.forgot_password_alert_message))
        alertFragment.show(parentFragmentManager, FORGOT_PASSWORD_ALERT_TAG)
    }

    private fun setUpButtonsListeners() {
        sign_in_button.setOnClickListener {
            val email = sign_in_email_input_edit_text.text.toString()
            val password = sign_in_password_input_edit_text.text.toString()
            val userCredentials = UserCredentials(email, password)
            viewModel.signIn(userCredentials)
        }

        sign_in_button_create_account.setOnClickListener {
            val extras = FragmentNavigatorExtras(it to "shared_element")
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment, null, null, extras)
        }

        sign_in_button_forgot_password.setOnClickListener {
            showForgotPasswordAlertDialog()
        }
    }

    private fun onSuccess(authResult: AuthResult) {
        showLoginState("Welcome " + authResult.user?.email)
        navigateTo(R.id.action_signInFragment_to_orderFragment)
    }

    private fun onFailure(exception: Exception) {
        showLoginState(exception.localizedMessage ?: "Error occurred")
    }

    companion object {
        const val FORGOT_PASSWORD_ALERT_TAG = "forgot_password_alert_tag"
    }
}
