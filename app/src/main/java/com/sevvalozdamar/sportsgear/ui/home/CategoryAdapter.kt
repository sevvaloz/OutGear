package com.sevvalozdamar.sportsgear.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sevvalozdamar.sportsgear.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val onCategoryClick: (String) -> Unit
) : ListAdapter<String, CategoryAdapter.CategoryViewHolder>(ProductDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onCategoryClick
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) =
        holder.bind(getItem(position))

    class CategoryViewHolder(
        private val binding: ItemCategoryBinding,
        private val onCategoryClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: String) {
            with(binding) {

                btnCategory.text = category

                btnCategory.setOnClickListener {
                    onCategoryClick(category)
                }
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