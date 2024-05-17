package com.aldiprahasta.storyapp.utils

import android.app.Activity
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

fun Activity.hideSystemUI(view: View) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowInsetsControllerCompat(window, view).let { controller ->
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun EditText.afterTextChanged(onTextChanged: (text: String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            onTextChanged.invoke(s.toString())
        }
    })
}

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}