package com.opus.common.customs

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.opus.ext.restoreChildViewStates
import com.opus.ext.saveChildViewStates
import com.opus.mobile.R
import com.opus.util.SavedState
import kotlinx.android.synthetic.main.progress_button.view.progress_button_icon
import kotlinx.android.synthetic.main.progress_button.view.progress_button_loading_indicator
import kotlinx.android.synthetic.main.progress_button.view.progress_button_text

class ProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    private val defStyleAttr: Int = 0,
    private val defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var isLoadingActivated = false

    init {
        inflate(context, R.layout.progress_button, this)
        isSaveEnabled = true
        applyAttrs(context, attrs)
    }

    private fun applyAttrs(context: Context, attrs: AttributeSet?) {
        if (!isInEditMode) {
            context.theme.obtainStyledAttributes(
                attrs, R.styleable.ProgressButton, defStyleAttr, defStyleRes
            ).apply {
                try {
                    progress_button_text.text = getString(R.styleable.ProgressButton_text)
                    isFocusable = true
                    isClickable = true
                } finally {
                    recycle()
                }
            }
        }
    }

    fun activateLoadingState() {
        isLoadingActivated = true
        progress_button_text.visibility = View.INVISIBLE
        progress_button_loading_indicator.visibility = View.VISIBLE
    }

    fun deactivateProgressIndicator(loadingResult: LoadingResult) {
        fun handleButtonState() {
            isLoadingActivated = false
            progress_button_loading_indicator.visibility = View.INVISIBLE
            progress_button_text.visibility = View.VISIBLE
        }
        when(loadingResult) {
            LoadingResult.SUCCESS -> {
                progress_button_text.text = resources.getString(R.string.progress_button_text_ok)
                progress_button_icon.visibility = View.VISIBLE
                handleButtonState()
            }
            LoadingResult.FAILURE -> { handleButtonState() }
        }
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>?) {
        dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>?) {
        dispatchThawSelfOnly(container)
    }

    override fun onSaveInstanceState(): Parcelable? {
        return SavedState(super.onSaveInstanceState()).apply {
            childrenStates = saveChildViewStates()
            isLoadingActivated = this@ProgressButton.isLoadingActivated
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        when (state) {
            is SavedState -> {
                super.onRestoreInstanceState(state.superState)
                state.childrenStates?.let { restoreChildViewStates(it) }
                if (state.isLoadingActivated) activateLoadingState()
            }
            else -> super.onRestoreInstanceState(state)
        }
    }
}

enum class LoadingResult {
    SUCCESS, FAILURE
}