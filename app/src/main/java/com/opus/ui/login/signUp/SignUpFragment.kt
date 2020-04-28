package com.opus.ui.login.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialContainerTransform
import com.google.firebase.auth.AuthResult
import com.opus.common.BaseFragment
import com.opus.data.entity.UserCredentials
import com.opus.ext.snackBar
import com.opus.mobile.R
import com.opus.ui.login.LoginViewModel
import com.opus.util.FirebaseObserver
import kotlinx.android.synthetic.main.fragment_sign_up.sign_up_button
import kotlinx.android.synthetic.main.fragment_sign_up.sign_up_email_input_edit_text
import kotlinx.android.synthetic.main.fragment_sign_up.sign_up_password_input_edit_text
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignUpFragment : BaseFragment() {

    private val viewModel: LoginViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.accountCreation.observe(viewLifecycleOwner, FirebaseObserver(::onSuccess, ::onFailure))

        setUpButtonListeners()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform()
    }

    private fun showLoginState(text: String) {
        snackBar(text, duration = Snackbar.LENGTH_LONG)
    }

    private fun setUpButtonListeners() {
        sign_up_button.setOnClickListener {
            val email = sign_up_email_input_edit_text.text.toString()
            val password = sign_up_password_input_edit_text.text.toString()
            val userCredentials = UserCredentials(email, password)
            viewModel.signUp(userCredentials)
        }
    }

    private fun onSuccess(authResult: AuthResult) = showLoginState("Account created. Check your email: " + authResult.user?.email)

    private fun onFailure(exception: Exception) = showLoginState(exception.localizedMessage ?: "Error occurred")
}
