package com.sevvalozdamar.sportsgear.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sevvalozdamar.sportsgear.data.model.Product
import com.sevvalozdamar.sportsgear.databinding.ItemProductBinding

class ProductAdapter(
    private val onProductClick: (Int) -> Unit
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onProductClick
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = holder.bind(getItem(position))

    class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val onProductClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            with(binding) {

                root.setOnClickListener {
                    onProductClick(product.id ?: 1)
                }
            }
        }
    }

    class ProductDiffUtilCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}