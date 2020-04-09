package com.opus.ext

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.toast(text: String, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(requireContext(), text, duration).show()

fun Fragment.snackBar(text: String, duration: Int = Snackbar.LENGTH_SHORT) = Snackbar.make(requireView(), text, duration).show()