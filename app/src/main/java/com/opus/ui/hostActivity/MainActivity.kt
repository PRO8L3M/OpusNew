package com.opus.ui.hostActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.opus.ext.dataBinding
import com.opus.mobile.BR
import com.opus.mobile.R
import com.opus.mobile.databinding.ActivityMainBinding
import com.opus.util.ToolbarInteractor
import kotlinx.android.synthetic.main.activity_main.toolbar

class MainActivity : AppCompatActivity(), ToolbarInteractor {

    private val binding: ActivityMainBinding by dataBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun setToolbar(hasHomeAsUp: Boolean) {
        binding.setVariable(BR.isActive, true)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        if (hasHomeAsUp) setHomeAsUp() else resetHomeAsUp()
    }

    override fun resetToolbar(isHomeAsUpEnabled: Boolean) {
        binding.setVariable(BR.isActive, false)
        setSupportActionBar(null)
        binding.toolbar.setNavigationOnClickListener(null)
    }

    private fun setHomeAsUp() = supportActionBar?.apply {
        setDisplayHomeAsUpEnabled(true)
        setHomeAsUpIndicator(R.drawable.ic_arrow_back)
    }

    private fun resetHomeAsUp() = supportActionBar?.setDisplayHomeAsUpEnabled(false)
}
