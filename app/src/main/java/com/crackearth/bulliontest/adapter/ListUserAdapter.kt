package com.crackearth.bulliontest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.crackearth.bulliontest.databinding.ItemUserBinding
import com.crackearth.bulliontest.model.DataItemUsersResponse

class ListUserAdapter : PagingDataAdapter<DataItemUsersResponse, ListUserAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(user: DataItemUsersResponse) {
                binding.tvName.text = user.name
                binding.tvEmail.text = user.email
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
            holder.itemView.setOnClickListener {
                Toast.makeText(holder.itemView.context, "tes", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun withLoadState(
        header: LoadStateAdapter<*>,
        footer: LoadStateAdapter<*>
    ): ConcatAdapter {
        addLoadStateListener { loadStates ->
            header.loadState = loadStates.refresh
            footer.loadState = when {
                loadStates.refresh is LoadState.NotLoading -> loadStates.append
                else -> loadStates.refresh
            }
        }
        return ConcatAdapter(header, this, footer)
    }

    companion object {
        private val TAG = "ListUserAdapter"
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemUsersResponse>() {
            override fun areItemsTheSame(oldItem: DataItemUsersResponse, newItem: DataItemUsersResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItemUsersResponse,
                newItem: DataItemUsersResponse
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}