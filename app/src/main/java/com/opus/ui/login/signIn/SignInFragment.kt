package com.opus.ui.login.signIn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opus.common.BaseFragment
import com.opus.mobile.R

class SignInFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    companion object {
        fun newInstance() = SignInFragment()
    }
}
