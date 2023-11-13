package com.crackearth.bulliontest.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.crackearth.bulliontest.databinding.ActivityLoginBinding
import com.crackearth.bulliontest.model.LoginRequest
import com.crackearth.bulliontest.utils.Utils.sha256
import com.crackearth.bulliontest.viewmodel.AuthViewModel
import com.crackearth.bulliontest.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: AuthViewModel by viewModels {
        ViewModelFactory.get
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

    }

}