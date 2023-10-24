package com.sevvalozdamar.sportsgear.ui.detail

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI.navigateUp
import com.bumptech.glide.Glide
import com.sevvalozdamar.sportsgear.R
import com.sevvalozdamar.sportsgear.databinding.FragmentDetailBinding
import com.sevvalozdamar.sportsgear.utils.invisible
import com.sevvalozdamar.sportsgear.utils.viewBinding
import com.sevvalozdamar.sportsgear.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)
    private val viewModel by viewModels<DetailViewModel>()
    private val args by navArgs<DetailFragmentArgs>()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProductDetail(args.id)
        observe()

        binding.ivBack.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
        }
    }


    private fun observe(){
        viewModel.productDetail.observe(viewLifecycleOwner){ product ->
            with(binding){
                itemTitle.text = product.title
                itemDescription.text = product.description
                itemCategory.text = product.category
                itemPrice.text = "$ ${product.price}"
                ratingBar.rating = product.rate.toFloat()
                Glide.with(itemImage).load(product.imageOne).into(itemImage)
                if(product.saleState){
                    itemPrice.text = "$ ${product.salePrice}"
                    itemOldPrice.text = product.price.toString()
                    itemOldPrice.paintFlags = itemOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    itemOldPrice.visible()
                } else {
                    itemPrice.text = "$ ${product.price}"
                    itemOldPrice.invisible()
                }
            }
        }
    }

}