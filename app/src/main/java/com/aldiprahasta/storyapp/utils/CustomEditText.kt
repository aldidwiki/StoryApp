package com.aldiprahasta.storyapp.utils

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.aldiprahasta.storyapp.R
import com.aldiprahasta.storyapp.databinding.CustomEditTextBinding

class CustomEditText @JvmOverloads constructor(
        mContext: Context,
        attrs: AttributeSet? = null
) : FrameLayout(mContext, attrs) {
    private val binding = CustomEditTextBinding.inflate(
            LayoutInflater.from(mContext), this, true
    )

    init {
        mContext.obtainStyledAttributes(attrs, R.styleable.CustomEditText).apply {
            val edtTitle = getString(R.styleable.CustomEditText_edtTitle)
            val edtLogo = getResourceId(R.styleable.CustomEditText_edtLogo, 0)
            val edtInputType = getInt(R.styleable.CustomEditText_edtInputType, InputType.TYPE_CLASS_TEXT)

            setupEditTextTitle(edtTitle)
            setupEditTextLogo(edtLogo)
            setupEditTextInputType(edtInputType)
        }.recycle()
    }

    private fun setupEditTextTitle(title: String?) {
        binding.tvTitle.text = title
    }

    private fun setupEditTextLogo(logo: Int) {
        if (logo != 0) binding.imgLogo.setImageResource(logo)
    }

    private fun setupEditTextInputType(type: Int) {
        binding.edtLayout.inputType = when (type) {
            PASSWORD_TYPE -> {
                setupEditTextOnTextChange()
                InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
            }

            EMAIL_TYPE -> InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
            else -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        }
    }

    private fun setupEditTextOnTextChange() {
        binding.edtLayout.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().length < 8) {
                    binding.edtLayout.error = "Password tidak boleh kurang dari 8 karakter"
                } else {
                    binding.edtLayout.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    companion object {
        private const val PASSWORD_TYPE = 2
        private const val EMAIL_TYPE = 3
    }
}