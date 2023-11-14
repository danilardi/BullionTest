package com.crackearth.bulliontest.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.crackearth.bulliontest.databinding.ItemUserBinding
import com.crackearth.bulliontest.model.DataItemUsersResponse
import com.crackearth.bulliontest.ui.DetailUserDialogFragment


class ListUserAdapter(private val data: List<DataItemUsersResponse>) : RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tvName.text = data[position].name
            tvEmail.text = data[position].email
        }

        holder.itemView.setOnClickListener {
//            Toast.makeText(holder.itemView.context, data[position].name, Toast.LENGTH_SHORT).show()
            val dialog = DetailUserDialogFragment()
            val b = Bundle()
            b.putInt("KEY", 1)
            b.putParcelableArray("DATA", )
            b.putpa
            dialog.arguments = b
            dialog.show((holder.itemView.context as FragmentActivity).supportFragmentManager, "detailUserDialog")
        }
    }

}