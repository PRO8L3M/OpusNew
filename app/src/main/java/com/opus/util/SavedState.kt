package com.opus.util

import android.os.Parcel
import android.os.Parcelable
import android.util.SparseArray
import android.view.View

internal class SavedState : View.BaseSavedState {

    var childrenStates: SparseArray<Parcelable>? = null
    var isLoadingActivated: Boolean = false

    constructor(superState: Parcelable?) : super(superState)

    constructor(source: Parcel) : super(source) {
        with(source) {
            childrenStates = readSparseArray(javaClass.classLoader)
            isLoadingActivated = readByte().toInt() != 0
        }

}

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        with(out) {
            writeSparseArray(childrenStates as SparseArray<Any>?)
            writeByte((if (isLoadingActivated) 1 else 0).toByte())
        }
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<SavedState> {
            override fun createFromParcel(source: Parcel) = SavedState(source)

            override fun newArray(size: Int): Array<SavedState?> = arrayOfNulls(size)
        }
    }
}