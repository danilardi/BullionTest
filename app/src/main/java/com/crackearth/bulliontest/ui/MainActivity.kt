package com.crackearth.bulliontest.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.crackearth.bulliontest.adapter.ListUserAdapter
import com.crackearth.bulliontest.adapter.LoadingStateAdapter
import com.crackearth.bulliontest.databinding.ActivityMainBinding
import com.crackearth.bulliontest.ui.auth.LoginActivity
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
            Toast.makeText(this, "yeay berhasil logout", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getListUser() {
        val listUserAdapter = ListUserAdapter()
        binding.rvUsers.adapter = listUserAdapter.withLoadState(
            header = LoadingStateAdapter {
                listUserAdapter.retry()
            },
            footer = LoadingStateAdapter {
                listUserAdapter.retry()
            }
        )

        mainViewModel.getAuth().observe(this) { user ->
            mainViewModel.getListUser("Bearer ${user.token}").observe(this) {
                listUserAdapter.submitData(lifecycle, it)
            }
        }
    }
    private fun recyclerViewSetup() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}