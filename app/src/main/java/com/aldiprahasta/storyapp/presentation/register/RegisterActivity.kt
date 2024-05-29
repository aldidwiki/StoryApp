package com.aldiprahasta.storyapp.presentation.register

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aldiprahasta.storyapp.data.source.network.request.RegisterRequestModel
import com.aldiprahasta.storyapp.databinding.ActivityRegisterBinding
import com.aldiprahasta.storyapp.utils.doIfError
import com.aldiprahasta.storyapp.utils.doIfLoading
import com.aldiprahasta.storyapp.utils.doIfSuccess
import com.aldiprahasta.storyapp.utils.gone
import com.aldiprahasta.storyapp.utils.visible
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModel<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        subscribeData()
    }

    private fun subscribeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isValidButton.collect { isValid ->
                        binding.btnSignup.isEnabled = isValid
                    }
                }

                launch {
                    viewModel.registerUser.collect { state ->
                        state.apply {
                            doIfLoading {
                                binding.pbRegister.visible()
                            }

                            doIfError { _, errorMessage ->
                                binding.pbRegister.gone()
                                Toast.makeText(this@RegisterActivity, errorMessage, Toast.LENGTH_SHORT).show()
                            }

                            doIfSuccess { model ->
                                binding.pbRegister.gone()
                                Toast.makeText(this@RegisterActivity, model.message, Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        binding.apply {
            imgBack.setOnClickListener {
                finish()
            }

            edtName.setOnTextChangedListener { name ->
                viewModel.setNameField(name)
            }

            edtEmail.setOnTextChangedListener { email ->
                viewModel.setEmailField(email)
            }

            edtPassword.setOnTextChangedListener { password ->
                viewModel.setPasswordField(password)
            }

            btnSignup.setOnClickListener {
                viewModel.setRegisterUserRequest(RegisterRequestModel(
                        name = binding.edtName.getText().trim(),
                        email = binding.edtEmail.getText().trim(),
                        password = binding.edtPassword.getText().trim()
                ))
            }
        }
    }
}