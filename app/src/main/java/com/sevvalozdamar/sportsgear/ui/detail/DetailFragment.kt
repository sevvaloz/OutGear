package com.sevvalozdamar.sportsgear.ui.detail

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
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

                        itemTitle.text = state.product.title
                        itemDescription.text = state.product.description
                        itemCategory.text = "${state.product.category}"
                        itemRate.text = "(${state.product.rate})"
                        ratingBar.rating = state.product.rate.toFloat()
                        itemStock.text = "${state.product.count} items left in stock."
                        Glide.with(itemImage).load(state.product.imageOne).into(itemImage)
                        if (state.product.saleState) {
                            itemPrice.text = "$${state.product.salePrice}"
                            itemOldPrice.text = state.product.price.toString()
                            itemOldPrice.paintFlags =
                                itemOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                            itemOldPrice.visible()
                        } else {
                            itemPrice.text = "$${state.product.price}"
                            itemOldPrice.invisible()
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
                        //POP UP GOSTER
                        //tvFail.text = state.errorMessage
                    }
                }
            }
        }
    }

}