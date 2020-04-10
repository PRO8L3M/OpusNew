package com.opus.ui.login.signIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.opus.common.BaseFragment
import com.opus.data.entity.FirebaseState
import com.opus.data.entity.UserCredentials
import com.opus.ext.navigateTo
import com.opus.ext.snackBar
import com.opus.mobile.R
import com.opus.ui.login.LoginViewModel
import com.opus.util.FirebaseObserver
import kotlinx.android.synthetic.main.fragment_sign_in.sign_in_button
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

        viewModel.accountLogin.observe(viewLifecycleOwner, FirebaseObserver(::onSuccess, ::onFailure))

        setUpButtonsListeners()
    }

    private fun showLoginState(text: String) {
       snackBar(text, duration = Snackbar.LENGTH_LONG)
    }

    private fun setUpButtonsListeners() {
        sign_in_button.setOnClickListener {
            val email = sign_in_email_input_edit_text.text.toString()
            val password = sign_in_password_input_edit_text.text.toString()
            val userCredentials = UserCredentials(email, password)
            // todo change that [PN]
            viewModel.signIn(userCredentials)
         //   navigateTo(R.id.action_firstFragment_to_orderFragment)
        }
    }

    private fun onSuccess(firebaseState: FirebaseState) {
        showLoginState(firebaseState.resultMessage)
        navigateTo(R.id.action_firstFragment_to_orderFragment)
    }

    private fun onFailure(exception: Exception) {
        showLoginState(exception.localizedMessage ?: "Error occurred")
    }

    companion object {
        fun newInstance() = SignInFragment()
    }
}
