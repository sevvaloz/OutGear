package com.sevvalozdamar.sportsgear.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sevvalozdamar.sportsgear.R
import com.sevvalozdamar.sportsgear.data.model.ProductUI
import com.sevvalozdamar.sportsgear.databinding.FragmentHomeBinding
import com.sevvalozdamar.sportsgear.ui.MainActivity
import com.sevvalozdamar.sportsgear.utils.PopupHelper
import com.sevvalozdamar.sportsgear.utils.gone
import com.sevvalozdamar.sportsgear.utils.viewBinding
import com.sevvalozdamar.sportsgear.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel by viewModels<HomeViewModel>()
    private val productAdapter = ProductAdapter(onProductClick = ::onProductClick, onFavClick = ::onFavClick)
    private val saleProductAdapter = ProductAdapter(onProductClick = ::onProductClick, onFavClick = ::onFavClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvProduct.adapter = productAdapter
            rvSalesProduct.adapter = saleProductAdapter

            ivLogout.setOnClickListener {
                signOut()
            }
        }

        viewModel.getProducts()
        observeData()
    }

    private fun observeData() {
        viewModel.homeState.observe(viewLifecycleOwner) { state ->
            binding.apply {
                when (state) {
                    HomeState.Loading -> {
                        binding.progressBar.visible()
                        cl.gone()
                        clFail.gone()
                    }

                    is HomeState.SuccessScreen -> {
                        binding.progressBar.gone()
                        clFail.gone()
                        cl.visible()
                        productAdapter.submitList(state.products)
                        saleProductAdapter.submitSaleProductList(state.products)
                    }

                    is HomeState.EmptyScreen -> {
                        binding.progressBar.gone()
                        cl.gone()
                        clFail.visible()
                        tvFail.text = state.failMessage
                    }

                    is HomeState.PopUpScreen -> {
                        binding.progressBar.gone()
                        cl.gone()
                        clFail.visible()
                        PopupHelper.showErrorPopup(requireContext(), state.errorMessage)
                    }
                }
            }
        }
    }

    private fun onProductClick(id: Int) {
        findNavController().navigate(HomeFragmentDirections.homeToDetail(id))
    }

    private fun onFavClick(product: ProductUI) {
        viewModel.setFavoriteState(product)
    }

    private fun signOut() {
        requireActivity().finish()
        viewModel.signuot()
        startActivity(Intent(requireActivity(), MainActivity::class.java))
    }

}