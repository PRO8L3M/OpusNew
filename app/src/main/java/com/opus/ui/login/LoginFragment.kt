package com.opus.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opus.common.BaseViewPagerFragment
import com.opus.common.customs.TabLayoutItem
import com.opus.mobile.R
import com.opus.ui.login.signIn.SignInFragment
import com.opus.ui.login.signUp.SignUpFragment
import kotlinx.android.synthetic.main.fragment_first.*

class LoginFragment : BaseViewPagerFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listOfFragments = listOf(
            TabLayoutItem("Sign In") { SignInFragment.newInstance() },
            TabLayoutItem("Sign Up") { SignUpFragment.newInstance() }
            )
        setupViewPager(login_view_pager, login_tab_layout, listOfFragments)
    }

    override fun onDestroyView() {
        onViewPagerDetached(login_view_pager)
        super.onDestroyView()
    }
}