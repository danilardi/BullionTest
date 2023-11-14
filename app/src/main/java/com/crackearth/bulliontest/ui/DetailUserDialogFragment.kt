package com.crackearth.bulliontest.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.crackearth.bulliontest.databinding.FragmentDetailUserDialogBinding

class DetailUserDialogFragment : DialogFragment() {

    private var _binding: FragmentDetailUserDialogBinding? = null
    private val binding get() = _binding!!
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

        val phone = arguments?.getInt("KEY")
        binding.tvPhone.text = phone.toString()

        binding.btnClose.setOnClickListener{
            onDestroyView()
        }

        binding.btnEdit.setOnClickListener {
            onDestroyView()
        }
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