package com.crackearth.bulliontest.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.crackearth.bulliontest.databinding.ActivityLoginBinding
import com.crackearth.bulliontest.model.LoginRequest
import com.crackearth.bulliontest.ui.MainActivity
import com.crackearth.bulliontest.utils.Response
import com.crackearth.bulliontest.utils.Utils.sha256
import com.crackearth.bulliontest.utils.dataStore
import com.crackearth.bulliontest.viewmodel.AuthViewModel
import com.crackearth.bulliontest.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val authViewModel: AuthViewModel by viewModels {
        ViewModelFactory.getInstance(dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            when {
                email.isEmpty() -> {
                    binding.edtEmail.error = "Masukkan email"
                    Toast.makeText(this@LoginActivity, "Masukkan email", Toast.LENGTH_SHORT).show()
                }

                password.isEmpty() -> {
                    binding.edtPassword.error = "Masukkan password"
                    Toast.makeText(this@LoginActivity, "Masukkan password", Toast.LENGTH_SHORT)
                        .show()
                }

                else -> {
                    val data = LoginRequest(
                        email,
                        password.sha256()
                    )
                    login(data)
                }
            }
        }
    }

    private fun login(data: LoginRequest) {
        showLoading(true)
        authViewModel.login(data).observe(this) { response ->
            when (response) {
                is Response.Success -> {
                    showLoading(false)
                    authViewModel.setAuth(response.data.dataLoginResponse)
                    Toast.makeText(this, "yeay berhasil login", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val TAG = "LoginActivity"
    }

}