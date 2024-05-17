package com.aldiprahasta.storyapp.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aldiprahasta.storyapp.data.request.LoginRequestModel
import com.aldiprahasta.storyapp.databinding.ActivityLoginBinding
import com.aldiprahasta.storyapp.presentation.home.HomeActivity
import com.aldiprahasta.storyapp.utils.MyPreferences
import com.aldiprahasta.storyapp.utils.doIfError
import com.aldiprahasta.storyapp.utils.doIfLoading
import com.aldiprahasta.storyapp.utils.doIfSuccess
import com.aldiprahasta.storyapp.utils.gone
import com.aldiprahasta.storyapp.utils.visible
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModel<LoginViewModel>()
    private val myPreferences by inject<MyPreferences>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        subscribeData()
    }

    private fun subscribeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isValidButton.collect { isValid ->
                        binding.btnLogin.isEnabled = isValid
                    }
                }

                launch {
                    viewModel.loginUser.collect { state ->
                        state.apply {
                            doIfLoading {
                                binding.pbLogin.visible()
                            }

                            doIfError { _, errorMessage ->
                                binding.pbLogin.gone()
                                Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()
                            }

                            doIfSuccess { model ->
                                binding.pbLogin.gone()
                                myPreferences.writeTokenToDataStore(model.token)
                                Intent(this@LoginActivity, HomeActivity::class.java).also { intent ->
                                    startActivity(intent)
                                    finish()
                                }
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

            btnLogin.setOnClickListener {
                viewModel.setLoginRequestModel(LoginRequestModel(
                        email = edtEmail.getText().trim(),
                        password = edtPassword.getText().trim()
                ))
            }

            edtEmail.setOnTextChangedListener { email ->
                viewModel.setEmailField(email)
            }

            edtPassword.setOnTextChangedListener { password ->
                viewModel.setPasswordField(password)
            }
        }
    }
}