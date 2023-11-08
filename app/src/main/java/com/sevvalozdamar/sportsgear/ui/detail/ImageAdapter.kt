package com.sevvalozdamar.sportsgear.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sevvalozdamar.sportsgear.databinding.ItemProductImageBinding

class ImageAdapter(
) : ListAdapter<String, ImageAdapter.CategoryViewHolder>(ProductDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemProductImageBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) =
        holder.bind(getItem(position))

    class CategoryViewHolder(
        private val binding: ItemProductImageBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            with(binding) {
                Glide.with(ivProductImage).load(imageUrl).into(ivProductImage)
            }
        }
    }

    class ProductDiffUtilCallBack : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}