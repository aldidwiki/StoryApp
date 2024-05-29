package com.aldiprahasta.storyapp.presentation.addstory

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aldiprahasta.storyapp.databinding.ActivityAddStoryBinding
import com.aldiprahasta.storyapp.utils.afterTextChanged
import com.aldiprahasta.storyapp.utils.doIfError
import com.aldiprahasta.storyapp.utils.doIfLoading
import com.aldiprahasta.storyapp.utils.doIfSuccess
import com.aldiprahasta.storyapp.utils.getImageUri
import com.aldiprahasta.storyapp.utils.gone
import com.aldiprahasta.storyapp.utils.reduceFileImage
import com.aldiprahasta.storyapp.utils.uriToFile
import com.aldiprahasta.storyapp.utils.visible
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class AddStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStoryBinding
    private val viewModel by viewModel<AddStoryViewModel>()

    private var currentImageUri: Uri? = null

    private val launcherGallery = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            viewModel.setImageUri(currentImageUri)
            showImage()
        } else {
            Timber.d("Photo Picker", "No media selected")
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
            ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        subscribeData()
    }

    private fun subscribeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isValidButton.collect { isValid ->
                        binding.btnUpload.isEnabled = isValid
                    }
                }

                launch {
                    viewModel.addStory.collect { state ->
                        state.apply {
                            doIfLoading {
                                binding.pbAddStory.visible()
                            }

                            doIfError { _, errorMessage ->
                                binding.pbAddStory.gone()
                                Toast.makeText(
                                        this@AddStoryActivity,
                                        errorMessage,
                                        Toast.LENGTH_SHORT
                                ).show()
                            }

                            doIfSuccess {
                                binding.pbAddStory.gone()
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
            btnGallery.setOnClickListener {
                startGallery()
            }

            btnCamera.setOnClickListener {
                startCamera()
            }

            btnUpload.setOnClickListener {
                uploadImage()
            }

            edtDescription.afterTextChanged { description ->
                viewModel.setDescriptionField(description)
            }

            imgBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Timber.d("Image File", "showImage: ${imageFile.path}")
            val description = binding.edtDescription.text.toString()

            val requestBody = description.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                    "photo",
                    imageFile.name,
                    requestImageFile
            )

            viewModel.setStoryData(Pair(multipartBody, requestBody))
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        viewModel.setImageUri(currentImageUri)
        currentImageUri?.let {
            launcherIntentCamera.launch(it)
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Timber.d("Image URI", "showImage: $it")
            binding.imgStory.setImageURI(it)
        }
    }
}