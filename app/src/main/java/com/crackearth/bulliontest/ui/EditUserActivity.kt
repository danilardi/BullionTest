package com.crackearth.bulliontest.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.crackearth.bulliontest.databinding.ActivityEditUserBinding
import com.crackearth.bulliontest.model.DataDetailUserResponse
import com.crackearth.bulliontest.ui.auth.LoginActivity
import com.crackearth.bulliontest.utils.Response
import com.crackearth.bulliontest.utils.dataStore
import com.crackearth.bulliontest.viewmodel.MainViewModel
import com.crackearth.bulliontest.viewmodel.ViewModelFactory
import com.google.android.material.datepicker.MaterialDatePicker
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EditUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditUserBinding

    private val editUserViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(dataStore)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(EXTRA_ID)
        getUserData(id!!)

        binding.tlBirth.setEndIconOnClickListener {
            getDate()
        }

        binding.backTopBar.btnBack.setOnClickListener {
            finish()
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getUserData(id: String) {
        editUserViewModel.getAuth().observe(this) { user ->
            if (user.token != "null") {
                showLoading(true)
                editUserViewModel.getDetailUser("Bearer ${user.token}", id)
                    .observe(this) { response ->
                        when (response) {
                            is Response.Success -> {
                                showLoading(false)
                                setUserData(response.data.data)
                            }

                            is Response.Error -> {
                                showLoading(false)
                                Toast.makeText(
                                    this,
                                    "Terjadi Kesalahan",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            is Response.Loading -> {
                                showLoading(true)
                            }

                            else -> {}
                        }
                    }
            } else {
                val intent = Intent(this@EditUserActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUserData(data: DataDetailUserResponse) {
        binding.edtName.setText(data.firstName.plus(" ").plus(data.lastName))
        binding.edtEmail.setText(data.email)
        binding.edtPhone.setText(data.phone)
        binding.edtAddress.setText(data.address)

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val date = LocalDate.parse(data.dateOfBirth, formatter)
        binding.edtBirth.setText(
            "${date.dayOfMonth} ${
                date.month.toString().lowercase().replaceFirstChar { it.uppercase() }
            } ${date.year}"
        )

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val TAG = "EditUserActivity"
        const val EXTRA_ID = "extra_id"
    }

}