package ru.btpit.nmedia.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

object AndroidUtils {
    fun hideKeyboard(view: View) {
        val imm = ContextCompat.getSystemService(view.context, InputMethodManager::class.java) ?: return
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}