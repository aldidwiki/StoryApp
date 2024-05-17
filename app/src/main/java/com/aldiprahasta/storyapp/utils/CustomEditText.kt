package com.aldiprahasta.storyapp.utils

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.aldiprahasta.storyapp.R
import com.aldiprahasta.storyapp.databinding.CustomEditTextBinding

class CustomEditText(
        mContext: Context,
        attrs: AttributeSet
) : FrameLayout(mContext, attrs) {
    private val binding = CustomEditTextBinding.inflate(
            LayoutInflater.from(mContext), this, true
    )

    init {
        mContext.obtainStyledAttributes(attrs, R.styleable.CustomEditText).apply {
            val edtTitle = getString(R.styleable.CustomEditText_edtTitle)
            val edtLogo = getResourceId(R.styleable.CustomEditText_edtLogo, 0)
            val isPasswordType = getBoolean(R.styleable.CustomEditText_isPasswordType, false)

            setupEditTextTitle(edtTitle)
            setupEditTextLogo(edtLogo)

            if (isPasswordType) {
                onPasswordType()
            }
        }.recycle()
    }

    private fun setupEditTextTitle(title: String?) {
        binding.tvTitle.text = title
    }

    private fun setupEditTextLogo(logo: Int) {
        if (logo != 0) binding.imgLogo.setImageResource(logo)
    }

    private fun onPasswordType() {
        var isOpenPassword = false
        binding.apply {
            imgEye.isVisible = true
            edtLayout.transformationMethod = PasswordTransformationMethod()
            edtLayout.filters = arrayOf(InputFilter.LengthFilter(12))
            setupEditTextOnTextChange()
            imgEye.setOnClickListener {
                isOpenPassword = !isOpenPassword
                if (isOpenPassword) {
                    imgEye.setImageResource(R.drawable.ic_eye_outline)
                    edtLayout.transformationMethod = null
                } else {
                    imgEye.setImageResource(R.drawable.ic_eye_filled)
                    edtLayout.transformationMethod = PasswordTransformationMethod()
                }
                edtLayout.setSelection(edtLayout.text.toString().length)
            }
        }
    }

    private fun setupEditTextOnTextChange() {
        binding.edtLayout.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().length < 8 && s.toString().isNotBlank()) {
                    setErrorMessage("Password tidak boleh kurang dari 8 karakter")
                } else {
                    setErrorMessage(null)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    fun getText() = binding.edtLayout.text.toString()

    fun setOnTextChangedListener(onTextChanged: (text: String) -> Unit) {
        binding.edtLayout.afterTextChanged {
            onTextChanged(it)
        }
    }

    fun setErrorMessage(text: CharSequence?) {
        text?.let {
            binding.tvError.isVisible = true
            binding.tvError.text = text
        } ?: run {
            binding.tvError.isVisible = false
        }
    }
}