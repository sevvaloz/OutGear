package com.sevvalozdamar.sportsgear.ui.detail

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.sevvalozdamar.sportsgear.R
import com.sevvalozdamar.sportsgear.databinding.FragmentDetailBinding
import com.sevvalozdamar.sportsgear.utils.Resource
import com.sevvalozdamar.sportsgear.utils.gone
import com.sevvalozdamar.sportsgear.utils.invisible
import com.sevvalozdamar.sportsgear.utils.viewBinding
import com.sevvalozdamar.sportsgear.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)
    private val viewModel by viewModels<DetailViewModel>()
    private val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProductDetail(args.id)
        observe()

        binding.ivBack.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
        }
    }


    private fun observe() {
        viewModel.productDetail.observe(viewLifecycleOwner) {
            binding.apply {
                when (it) {
                    Resource.Loading -> {
                        progressBar.visible()
                        cl.gone()
                    }

                    is Resource.Success -> {
                        binding.progressBar.gone()
                        cl.visible()

                        itemTitle.text = it.data.title
                        itemDescription.text = it.data.description
                        itemCategory.text = it.data.category
                        ratingBar.rating = it.data.rate.toFloat()
                        itemStock.text = "Hurry up! Only ${it.data.count} items left in stock."
                        Glide.with(itemImage).load(it.data.imageOne).into(itemImage)
                        if (it.data.saleState) {
                            itemPrice.text = "$${it.data.salePrice}"
                            itemOldPrice.text = it.data.price.toString()
                            itemOldPrice.paintFlags =
                                itemOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                            itemOldPrice.visible()
                        } else {
                            itemPrice.text = "$${it.data.price}"
                            itemOldPrice.invisible()
                        }
                    }

                    is Resource.Fail -> {
                        binding.progressBar.gone()
                        cl.visible()
                        Snackbar.make(requireView(), it.failMessage, 2000).show()
                    }

                    is Resource.Error -> {
                        binding.progressBar.gone()
                        cl.visible()
                        Snackbar.make(requireView(), it.errorMessage, 2000).show()
                    }
                }

            }

        }
    }

}