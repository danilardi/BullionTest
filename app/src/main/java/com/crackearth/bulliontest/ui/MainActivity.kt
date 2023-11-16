package com.crackearth.bulliontest.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.crackearth.bulliontest.adapter.ListUserAdapter
import com.crackearth.bulliontest.databinding.ActivityMainBinding
import com.crackearth.bulliontest.model.DataUserResponse
import com.crackearth.bulliontest.ui.auth.LoginActivity
import com.crackearth.bulliontest.ui.auth.RegisterActivity
import com.crackearth.bulliontest.utils.Response
import com.crackearth.bulliontest.utils.dataStore
import com.crackearth.bulliontest.viewmodel.MainViewModel
import com.crackearth.bulliontest.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerViewSetup()
        getListUser()

        binding.appBar.tvLogout.setOnClickListener {
            mainViewModel.clearSession()
            Toast.makeText(this, "berhasil logout", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getListUser() {
        mainViewModel.getAuth().observe(this) { user ->
            if (user.token != "null") {
                showLoading(true)
                mainViewModel.getListUser("Bearer ${user.token}").observe(this) { response ->
                    when (response) {
                        is Response.Success -> {
                            showLoading(false)
                            setUserData(response.data.data)
                        }

                        is Response.Error -> {
                            showLoading(false)
                            Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                        }

                        is Response.Loading -> {
                            showLoading(true)
                        }

                        else -> {}
                    }
                }
            } else {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
    }

    private fun setUserData(listUser: List<DataUserResponse>) {
        val adapter = ListUserAdapter(listUser)
        binding.rvUsers.adapter = adapter
    }

    private fun recyclerViewSetup() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}