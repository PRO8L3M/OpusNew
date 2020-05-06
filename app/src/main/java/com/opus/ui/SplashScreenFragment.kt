package com.opus.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opus.common.BaseFragment
import com.opus.common.SPLASH_SCREEN_DURATION
import com.opus.ext.navigateTo
import com.opus.mobile.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        run {
            viewScope.launch {
                delay(SPLASH_SCREEN_DURATION)
                navigateTo(R.id.action_splashScreenFragment_to_signInFragment)
            }
        }
    }
}
