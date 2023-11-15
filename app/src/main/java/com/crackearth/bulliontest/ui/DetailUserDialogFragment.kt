package com.crackearth.bulliontest.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.crackearth.bulliontest.databinding.FragmentDetailUserDialogBinding
import com.crackearth.bulliontest.model.DataDetailUserResponse
import com.crackearth.bulliontest.ui.auth.LoginActivity
import com.crackearth.bulliontest.utils.Response
import com.crackearth.bulliontest.utils.dataStore
import com.crackearth.bulliontest.viewmodel.MainViewModel
import com.crackearth.bulliontest.viewmodel.ViewModelFactory
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class DetailUserDialogFragment : DialogFragment() {

    private var _binding: FragmentDetailUserDialogBinding? = null
    private val binding get() = _binding!!

    private val detailUserViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext().dataStore)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        _binding = FragmentDetailUserDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString("ID", "")

        getUserData(id!!)

        binding.btnClose.setOnClickListener {
            onDestroyView()
        }

        binding.btnEdit.setOnClickListener {
            onDestroyView()
        }
    }

    private fun getUserData(id: String) {
        detailUserViewModel.getAuth().observe(this) { user ->
            if (user.token != "null") {
                showLoading(true)
                detailUserViewModel.getDetailUser("Bearer ${user.token}", id)
                    .observe(this) { response ->
                        when (response) {
                            is Response.Success -> {
                                showLoading(false)
                                setUserData(response.data.data)
                            }

                            is Response.Error -> {
                                showLoading(false)
                                Toast.makeText(
                                    requireContext(),
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
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                onDestroyView()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUserData(data: DataDetailUserResponse) {
        binding.tvName.text = data.firstName.plus(" ").plus(data.lastName)
        binding.tvEmail.text = data.email
        binding.tvGender.text = data.gender
        binding.tvPhone.text = data.phone
        binding.tvBirth.text = data.dateOfBirth
        binding.tvAddress.text = data.address

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val date = LocalDate.parse(data.dateOfBirth, formatter)
        binding.tvBirth.text = "${date.dayOfMonth} ${date.month.toString().lowercase().replaceFirstChar { it.uppercase() }} ${date.year}"

        val imageBytes = Base64.decode(data.photo, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        decodedImage.compress(Bitmap.CompressFormat.JPEG, 10, ByteArrayOutputStream())
        binding.ivUserPhoto.setImageBitmap(decodedImage)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "DetailUserDialogFragment"
        const val KEY = "key"
    }
}