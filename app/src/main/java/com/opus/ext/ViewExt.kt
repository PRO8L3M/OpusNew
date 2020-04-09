package com.opus.ext

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.opus.common.NO_FLAG

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, NO_FLAG)
}

inline fun <reified T> RecyclerView.Adapter<*>.getAsyncListDiffer(noinline areItemsTheSame: ((oldItem: T, newItem:T) -> Boolean)): AsyncListDiffer<T> {
    val diffUtilCallback = object: DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T) = areItemsTheSame.invoke(oldItem, newItem)

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem
    }
    return AsyncListDiffer(this, diffUtilCallback)
}