package com.crackearth.bulliontest.ui.auth

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import com.crackearth.bulliontest.databinding.ActivityRegisterBinding
import com.crackearth.bulliontest.model.RegisterRequest
import com.crackearth.bulliontest.utils.Response
import com.crackearth.bulliontest.utils.Utils.reduceFileImage
import com.crackearth.bulliontest.utils.Utils.sha256
import com.crackearth.bulliontest.utils.Utils.toIsoString
import com.crackearth.bulliontest.utils.Utils.uriToFile
import com.crackearth.bulliontest.utils.dataStore
import com.crackearth.bulliontest.viewmodel.AuthViewModel
import com.crackearth.bulliontest.viewmodel.ViewModelFactory
import com.google.android.material.datepicker.MaterialDatePicker
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val authViewModel: AuthViewModel by viewModels {
        ViewModelFactory.getInstance(dataStore)
    }

    private var getFile: File? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tlBirth.setEndIconOnClickListener {
            getDate()
        }


        binding.tlPhotoProfile.setEndIconOnClickListener {
            startGallery()
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.edtName.text.toString()
            val genderMale = binding.cbMale.text.toString()
            val genderFemale = binding.cbFemale
            val email = binding.edtEmail.text.toString()
            val phone = binding.edtPhone.text.toString()
            val address = binding.edtAddress.text.toString()
            val password = binding.edtPassword.text.toString()
            val confirmPassword = binding.edtConfirmPassword.text.toString()

            val formatter = DateTimeFormatter.ofPattern("d MMM yyyy")
            val current = LocalDate.parse(binding.edtBirth.text, formatter)
            val birth = current.toIsoString()

            when {
                name.isEmpty() -> {
                    binding.edtName.error = "Masukkan nama"
                    Toast.makeText(this@RegisterActivity, "lengkapi data", Toast.LENGTH_SHORT).show()
                }
                email.isEmpty() -> {
                    binding.edtEmail.error = "Masukkan email"
                    Toast.makeText(this@RegisterActivity, "lengkapi data", Toast.LENGTH_SHORT).show()
                }
                password.isEmpty() -> {
                    binding.edtPassword.error = "Masukkan password"
                    Toast.makeText(this@RegisterActivity, "lengkapi data", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val data = RegisterRequest(
                        name,
                        name,
                        genderMale,
                        birth,
                        email,
                        phone,
                        address,
                        password.sha256()
                    )
                    register(data)
                }
            }
        }

        binding.backTopBar.btnBack.setOnClickListener{
            finish()
        }
    }

    private fun register(data: RegisterRequest) {
        val file = reduceFileImage(getFile as File)
        val datades = data.toString().toRequestBody("multipart/form-data".toMediaType())
        showLoading(true)
        authViewModel.register(file, data).observe(this) { response ->
            when (response) {
                is Response.Success -> {
                    showLoading(false)
                    Toast.makeText(this, "yeay berhasil daftar", Toast.LENGTH_SHORT).show()
                    finish()
                }
                is Response.Error -> {
                    showLoading(false)
                    Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                }
                is Response.Loading -> {
                    showLoading(true)
                }
            }
        }
    }

    private fun getDate() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Pilih Tanggal")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
        datePicker.show(supportFragmentManager, "date")

        datePicker.addOnPositiveButtonClickListener {
            val time = datePicker.headerText
            binding.edtBirth.setText(time)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            Log.d(TAG, "tess: ${result.data!!.data}}")

            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@RegisterActivity)
                getFile = myFile
                binding.edtPhotoProfile.setText(DocumentFile.fromSingleUri(this@RegisterActivity, uri)!!.name)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val TAG = "RegisterActivity"
    }
}