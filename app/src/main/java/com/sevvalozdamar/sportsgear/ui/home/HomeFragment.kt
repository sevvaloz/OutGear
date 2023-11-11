package com.sevvalozdamar.sportsgear.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sevvalozdamar.sportsgear.R
import com.sevvalozdamar.sportsgear.data.model.ProductUI
import com.sevvalozdamar.sportsgear.databinding.FragmentHomeBinding
import com.sevvalozdamar.sportsgear.ui.main.MainActivity
import com.sevvalozdamar.sportsgear.utils.PopupHelper
import com.sevvalozdamar.sportsgear.utils.Resource
import com.sevvalozdamar.sportsgear.utils.gone
import com.sevvalozdamar.sportsgear.utils.viewBinding
import com.sevvalozdamar.sportsgear.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel by viewModels<HomeViewModel>()
    private val productAdapter = ProductAdapter(onProductClick = ::onProductClick, onFavClick = ::onFavClick, onCartClick = ::onCartClick)
    private val saleProductAdapter = ProductAdapter(onProductClick = ::onProductClick, onFavClick = ::onFavClick, onCartClick = ::onCartClick)
    private val categoryAdapter = CategoryAdapter(onCategoryClick = ::onCategoryClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            rvProduct.adapter = productAdapter
            rvCategory.adapter = categoryAdapter
            rvSalesProduct.adapter = saleProductAdapter

            ivLogout.setOnClickListener {
                signOut()
            }
        }

        viewModel.getProducts()
        viewModel.getCategories()
        observeData()
        observeCategoryData()
        observeUser()
    }

    private fun observeData() {
        viewModel.homeState.observe(viewLifecycleOwner) { state ->
            binding.apply {
                when (state) {
                    HomeState.Loading -> {
                        progressBar.visible()
                        cl.gone()
                    }

                    is HomeState.SuccessScreen -> {
                        productAdapter.submitList(state.products)
                        saleProductAdapter.submitSaleProductList(state.products)

                        progressBar.gone()
                        cl.visible()
                        tvProducts.visible()
                        tvSale.visible()
                        line.visible()
                    }

                    is HomeState.EmptyScreen -> {
                        progressBar.gone()
                        cl.gone()
                        clFail.visible()
                        tvFail.text = state.failMessage
                    }

                    is HomeState.PopUpScreen -> {
                        progressBar.gone()
                        cl.gone()
                        PopupHelper.showErrorPopup(requireContext(), state.errorMessage)
                    }
                }
            }
        }

        viewModel.productByCategoryState.observe(viewLifecycleOwner){ state ->
            binding.apply {
                when(state){
                    HomeState.Loading -> {
                        progressBar.visible()
                        cl.gone()
                    }
                    is HomeState.SuccessScreen -> {
                        productAdapter.submitList(state.products)

                        progressBar.gone()
                        cl.visible()
                        tvProducts.visible()
                        tvSale.visible()
                        line.visible()
                    }
                    is HomeState.EmptyScreen -> {
                        progressBar.gone()
                        cl.gone()
                        Snackbar.make(requireView(), state.failMessage, 1000)
                            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.warning))
                            .show()
                    }
                    is HomeState.PopUpScreen -> {
                        progressBar.gone()
                        cl.gone()
                        PopupHelper.showErrorPopup(requireContext(), state.errorMessage)
                    }
                }
            }
        }
    }

    private fun observeCategoryData(){
        viewModel.categoryState.observe(viewLifecycleOwner){ state ->
            binding.apply {
                when(state){
                    CategoryState.Loading -> {
                        progressBar.visible()
                        cl.gone()
                    }
                    is CategoryState.SuccessScreen -> {
                        categoryAdapter.submitList(state.categories)

                        progressBar.gone()
                        rvCategory.visible()
                        cl.visible()
                        tvProducts.visible()
                        tvSale.visible()
                        line.visible()
                    }
                    is CategoryState.FailMessage -> {
                        progressBar.gone()
                        cl.gone()
                        Snackbar.make(requireView(), state.failMessage, 1000)
                            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.warning))
                            .show()
                    }
                    is CategoryState.PopUpScreen -> {
                        progressBar.gone()
                        cl.gone()
                        PopupHelper.showErrorPopup(requireContext(), state.errorMessage)
                    }
                }
            }
        }
    }

    private fun observeUser(){
        binding.apply {
            viewModel.user.observe(viewLifecycleOwner){ state ->
                when(state){
                    Resource.Loading -> {
                        progressBar.visible()
                        cl.gone()
                        tvHello.gone()
                    }
                    is Resource.Success -> {
                        progressBar.gone()
                        tvHello.visible()
                        tvHello.text = "Hello ${state.data.name}"
                    }
                    is Resource.Fail -> {
                        progressBar.gone()
                        cl.gone()
                        tvHello.gone()
                        Snackbar.make(requireView(), state.failMessage, 1000)
                            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.warning))
                            .show()
                    }
                    is Resource.Error -> {
                        progressBar.gone()
                        cl.gone()
                        tvHello.gone()
                        PopupHelper.showErrorPopup(requireContext(), state.errorMessage)
                    }
                }
            }
        }
    }

    private fun observeCartData(){
        viewModel.addToCartState.observe(viewLifecycleOwner){ state ->
            binding.apply {
                when(state){
                    AddToCartState.Loading -> {
                        progressBar.visible()
                        cl.gone()
                    }

                    is AddToCartState.SuccessMessage -> {
                        progressBar.gone()
                        cl.visible()
                        tvProducts.visible()
                        tvSale.visible()
                        line.visible()
                        Snackbar.make(requireView(), state.message, 1000)
                            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.success))
                            .show()
                    }

                    is AddToCartState.FailMessage -> {
                        progressBar.gone()
                        cl.visible()
                        tvProducts.visible()
                        tvSale.visible()
                        line.visible()
                        Snackbar.make(requireView(), state.failMessage, 1000)
                            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.warning))
                            .show()
                    }

                    is AddToCartState.PopUpScreen -> {
                        progressBar.gone()
                        cl.gone()
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
        if (product.isFav) {
            Snackbar.make(
                requireView(),
                "${product.title} is deleted from favorites",
                1000
            )
                .setBackgroundTint(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.success
                    )
                )
                .show()
        } else if (!product.isFav) {
            Snackbar.make(
                requireView(),
                "${product.title} is added to favorites",
                1000
            )
                .setBackgroundTint(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.success
                    )
                )
                .show()
        }
    }

    private fun onCartClick(product: ProductUI){
        viewModel.addToCart(product.id)
        observeCartData()
    }

    private fun onCategoryClick(category: String){
        viewModel.getProductsByCategory(category)
        println(category)
    }

    private fun signOut() {
        requireActivity().finish()
        viewModel.signOut()
        startActivity(Intent(requireActivity(), MainActivity::class.java))
    }

}