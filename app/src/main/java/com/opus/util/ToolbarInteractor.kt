package com.opus.util

interface ToolbarInteractor {
    fun setToolbar(hasHomeAsUp: Boolean = false)
    fun resetToolbar(isHomeAsUpEnabled: Boolean = false)
}