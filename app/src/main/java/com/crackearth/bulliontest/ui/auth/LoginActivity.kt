package com.crackearth.bulliontest.ui.auth

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
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
    private var loginValid = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isLogin()

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if (isLoginValid()) {
                val data = LoginRequest(
                    email, password.sha256()
                )
                login(data)
            } else {
                Toast.makeText(this@LoginActivity, "Lengkapi Data", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(data: LoginRequest) {
        showLoading(true)
        authViewModel.login(data).observe(this) { response ->
            when (response) {
                is Response.Success -> {
                    showLoading(false)
                    authViewModel.setAuth(response.data.dataLoginResponse)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                is Response.Error -> {
                    showLoading(false)
                    Toast.makeText(this, response.error, Toast.LENGTH_SHORT).show()
                }

                is Response.Loading -> {
                    showLoading(true)
                }

            }
        }
    }

    private fun isLogin() {
        showLoading(true)
        authViewModel.getAuth().observe(this) { user ->
            if (user.token != "null") {
                showLoading(false)
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
            showLoading(false)

        }
    }

    private fun isLoginValid(): Boolean {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        when {
            email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.edtEmail.error = "Wrong Format"
            }

            password.isEmpty() || password.length < 8 -> {
                binding.edtPassword.error = "Minimum 8 character"
            }

            else -> {
                return true
            }
        }
        return false

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val TAG = "LoginActivity"
    }

}