package com.opus.common.customs.progressButton

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.opus.ext.restoreChildViewStates
import com.opus.ext.saveChildViewStates
import com.opus.mobile.BR
import com.opus.mobile.R
import com.opus.mobile.databinding.ProgressButtonBinding
import com.opus.util.SavedState

class ProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    private val defStyleAttr: Int = 0,
    private val defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var isLoadingActivated = false
    private var binding: ProgressButtonBinding =
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.progress_button, this, true)

    init {
        applyAttrs(context, attrs)
        binding.viewState = ViewState.ACTIVE
    }

    private fun applyAttrs(context: Context, attrs: AttributeSet?) {
        if (!isInEditMode) {
            context.theme.obtainStyledAttributes(
                attrs, R.styleable.ProgressButton, defStyleAttr, defStyleRes
            ).apply {
                try {
                    binding.setVariable(BR.buttonText, getString(R.styleable.ProgressButton_text))
                } finally {
                    recycle()
                }
            }
        }
    }

    private fun handleViewActivation(isActive: Boolean) {
        fun handleActivation(isActive: Boolean) {
            isClickable = isActive
            isActivated = isActive
        }
        if (isActive) handleActivation(true) else handleActivation(false)
    }

    fun activateLoadingState() {
        isLoadingActivated = true
        binding.setVariable(BR.viewState, ViewState.LOADING)
        handleViewActivation(false)
    }

    fun deactivateProgressIndicator(loadingResult: LoadingResult) {
        isLoadingActivated = false
        when(loadingResult) {
            LoadingResult.SUCCESS -> with(binding) {
                setVariable(BR.buttonText, resources.getString(R.string.progress_button_text_ok))
                setVariable(BR.viewState, ViewState.FINISHED)
            }
            LoadingResult.FAILURE ->  {
                binding.setVariable(BR.viewState, ViewState.ACTIVE)
                handleViewActivation(true)
            }
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
                if (state.isLoadingActivated) binding.setVariable(BR.viewState, ViewState.LOADING)
            }
            else -> super.onRestoreInstanceState(state)
        }
    }
}

enum class LoadingResult {
    SUCCESS, FAILURE
}

enum class ViewState {
    ACTIVE, LOADING, FINISHED;

    fun isTextVisible() = when(this) {
        ACTIVE -> true
        LOADING -> false
        FINISHED -> true
    }

    fun isProgressVisible() = when(this) {
        ACTIVE -> false
        LOADING -> true
        FINISHED -> false
    }

    fun isIconVisible() = when(this) {
        ACTIVE -> false
        LOADING -> false
        FINISHED -> true
    }
}
