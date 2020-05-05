package com.opus.util

import androidx.lifecycle.Observer
import com.opus.data.entity.FirebaseResult

class FirebaseObserver<T : Any>(
    private val onSuccess: (T) -> Unit,
    private val onFailure: (Exception) -> Unit,
    private val onLoading: () -> Unit
) : Observer<FirebaseResult<T>> {

    override fun onChanged(result: FirebaseResult<T>) {
        when (result) {
            is FirebaseResult.Success -> onSuccess(result.data)
            is FirebaseResult.Failure -> onFailure(result.exception)
            is FirebaseResult.Loading -> onLoading()
        }
    }
}