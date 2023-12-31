package com.sevvalozdamar.sportsgear.ui.search

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sevvalozdamar.sportsgear.data.model.ProductUI
import com.sevvalozdamar.sportsgear.databinding.ItemProductBinding
import com.sevvalozdamar.sportsgear.utils.gone
import com.sevvalozdamar.sportsgear.utils.invisible
import com.sevvalozdamar.sportsgear.utils.visible

class SearchAdapter(
    private val onProductClick: (Int) -> Unit
) : ListAdapter<ProductUI, SearchAdapter.SearchViewHolder>(ProductDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onProductClick
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) =
        holder.bind(getItem(position))

    class SearchViewHolder(
        private val binding: ItemProductBinding,
        private val onProductClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductUI) {
            with(binding) {
                btnDelete.gone()
                itemTitle.text = product.title
                Glide.with(itemImage).load(product.imageOne).into(itemImage)
                if (product.saleState) {
                    itemPrice.text = "$${product.salePrice}"
                    itemOldPrice.text = "$${product.price}"
                    itemOldPrice.paintFlags = itemOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    itemOldPrice.visible()
                } else {
                    itemPrice.text = "$${product.price}"
                    itemOldPrice.invisible()
                }

                root.setOnClickListener {
                    onProductClick(product.id)
                }
            }
        }
    }

    class ProductDiffUtilCallBack : DiffUtil.ItemCallback<ProductUI>() {
        override fun areItemsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem == newItem
        }
    }

}