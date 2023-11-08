package com.sevvalozdamar.sportsgear.ui.detail

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.sevvalozdamar.sportsgear.R
import com.sevvalozdamar.sportsgear.databinding.FragmentDetailBinding
import com.sevvalozdamar.sportsgear.ui.home.AddToCartState
import com.sevvalozdamar.sportsgear.ui.home.CategoryAdapter
import com.sevvalozdamar.sportsgear.ui.home.HomeViewModel
import com.sevvalozdamar.sportsgear.utils.PopupHelper
import com.sevvalozdamar.sportsgear.utils.gone
import com.sevvalozdamar.sportsgear.utils.invisible
import com.sevvalozdamar.sportsgear.utils.viewBinding
import com.sevvalozdamar.sportsgear.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)
    private val viewModel by viewModels<DetailViewModel>()
    private val homeViewModel by viewModels<HomeViewModel>()
    private val args by navArgs<DetailFragmentArgs>()
    private val imageAdapter = ImageAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            rvImages.adapter = imageAdapter

            ivBack.setOnClickListener {
                val fragmentManager = requireActivity().supportFragmentManager
                fragmentManager.popBackStack()
            }
            btnAddToCart.setOnClickListener {
                homeViewModel.addToCart(args.id)
                observeCartData()
            }

        }

        viewModel.getProductDetail(args.id)
        observeData()
    }

    private fun observeData() {
        viewModel.detailState.observe(viewLifecycleOwner) { state ->
            binding.apply {

                when (state) {
                    DetailState.Loading -> {
                        progressBar.visible()
                        cl.gone()
                        clFail.gone()
                    }

                    is DetailState.SuccessScreen -> {
                        binding.progressBar.gone()
                        clFail.gone()
                        cl.visible()
                        btnAddToCart.visible()
                        itemOldPrice.visible()
                        itemPrice.visible()

                        itemTitle.text = state.product.title
                        itemDescription.text = state.product.description
                        itemCategory.text = "${state.product.category}"
                        itemRate.text = "${state.product.rate}"
                        ratingBar.rating = state.product.rate.toFloat()
                        itemStock.text = "${state.product.count} items left in stock"

                        //
                        imageAdapter.submitList(listOf(state.product.imageOne, state.product.imageTwo, state.product.imageThree))


                        if (state.product.saleState) {
                            itemPrice.text = "$${state.product.salePrice}"
                            itemOldPrice.text = "$${state.product.price}"
                            itemOldPrice.paintFlags =
                                itemOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                            itemOldPrice.visible()
                        } else {
                            itemPrice.text = "$${state.product.price}"
                            itemOldPrice.invisible()
                        }
                        ivFav.setBackgroundResource(
                            if (state.product.isFav) R.drawable.asset_favorite
                            else R.drawable.asset_favorite_border
                        )
                        ivFav.setOnClickListener {
                            viewModel.setFavoriteState(state.product)
                        }
                    }

                    is DetailState.EmptyScreen -> {
                        binding.progressBar.gone()
                        cl.gone()
                        clFail.visible()
                        tvFail.text = state.failMessage
                    }

                    is DetailState.PopUpScreen -> {
                        binding.progressBar.gone()
                        cl.gone()
                        clFail.visible()
                        PopupHelper.showErrorPopup(requireContext(), state.errorMessage)
                    }
                }
            }
        }
    }

    private fun observeCartData(){
        homeViewModel.addToCartState.observe(viewLifecycleOwner) { state ->
            binding.apply {
                when (state) {
                    AddToCartState.Loading -> {
                        cl.gone()
                        clFail.gone()
                    }

                    is AddToCartState.SuccessMessage -> {
                        progressBar.gone()
                        clFail.gone()
                        cl.visible()
                        btnAddToCart.visible()
                        itemOldPrice.visible()
                        itemPrice.visible()

                        Snackbar.make(requireView(), state.message, 1000)
                            .setBackgroundTint(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.success
                                )
                            )
                            .show()
                    }

                    is AddToCartState.FailMessage -> {
                        progressBar.gone()
                        clFail.gone()
                        cl.visible()
                        btnAddToCart.visible()
                        itemOldPrice.visible()
                        itemPrice.visible()

                        Snackbar.make(requireView(), state.failMessage, 1000)
                            .setBackgroundTint(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.warning
                                )
                            )
                            .show()
                    }

                    is AddToCartState.PopUpScreen -> {
                        progressBar.gone()
                        cl.gone()
                        clFail.visible()
                        PopupHelper.showErrorPopup(requireContext(), state.errorMessage)
                    }
                }
            }
        }
    }
}