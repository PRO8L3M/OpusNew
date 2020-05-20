package com.opus.ui.splashScreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.opus.ext.navigateTo
import com.opus.mobile.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {

    private val viewModel: SplashScreenViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isReadyToNavigate.observe(
            viewLifecycleOwner,
            Observer { isReadyToNavigate -> if (isReadyToNavigate) navigateTo(R.id.action_splashScreenFragment_to_signInFragment) })

        viewModel.startSplashScreen()
    }
}
