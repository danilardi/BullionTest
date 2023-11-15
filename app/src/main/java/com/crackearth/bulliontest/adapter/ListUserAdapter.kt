package com.crackearth.bulliontest.adapter

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.crackearth.bulliontest.databinding.ItemUserBinding
import com.crackearth.bulliontest.model.DataUserResponse
import com.crackearth.bulliontest.ui.DetailUserDialogFragment
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class ListUserAdapter(private val data: List<DataUserResponse>) : RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = data.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val imageBytes = Base64.decode(data[position].photo, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            ivUserPhoto.setImageBitmap(decodedImage)

            tvName.text = data[position].name
            tvEmail.text = data[position].email

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val date = LocalDate.parse(data[position].dateOfBirth, formatter)
            tvBirth.text = "${date.month.toString().lowercase().replaceFirstChar { it.uppercase() }} ${date.dayOfMonth}, ${date.year}"
        }

        holder.itemView.setOnClickListener {
            val dialog = DetailUserDialogFragment()
            val b = Bundle()
            b.putString("ID", data[position].id)
            dialog.arguments = b
            dialog.show((holder.itemView.context as FragmentActivity).supportFragmentManager, "detailUserDialog")
        }
    }

    companion object {
        private const val TAG = "ListUserAdapter"
    }

}