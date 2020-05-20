package com.opus.util

import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.databinding.BindingAdapter

@BindingAdapter("app:toolbarVisibility")
fun View.setToolbarVisibility(isActive: Boolean?) =
    isActive?.let { visibility = if (isActive) VISIBLE else GONE } ?: GONE

@BindingAdapter("app:viewVisibility")
fun View.setViewVisibility(isVisible: Boolean) {
    visibility = if (isVisible) VISIBLE else INVISIBLE
}