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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                        clFail.gone()
                    }

                    is HomeState.SuccessScreen -> {
                        progressBar.gone()
                        clFail.gone()
                        cl.visible()
                        productAdapter.submitList(state.products)
                        saleProductAdapter.submitSaleProductList(state.products)
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
                        clFail.visible()
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
                        clFail.gone()
                    }
                    is HomeState.SuccessScreen -> {
                        progressBar.gone()
                        clFail.gone()
                        cl.visible()
                        productAdapter.submitList(state.products)
                    }
                    is HomeState.EmptyScreen -> {
                        progressBar.gone()
                        clFail.gone()
                        cl.visible()
                        Snackbar.make(requireView(), state.failMessage, 1000)
                            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.warning))
                            .show()
                    }
                    is HomeState.PopUpScreen -> {
                        progressBar.gone()
                        cl.gone()
                        clFail.visible()
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
                        clFail.gone()
                    }
                    is CategoryState.SuccessScreen -> {
                        progressBar.gone()
                        clFail.gone()
                        cl.visible()
                        categoryAdapter.submitList(state.categories)
                    }
                    is CategoryState.FailMessage -> {
                        progressBar.gone()
                        clFail.gone()
                        cl.visible()
                        Snackbar.make(requireView(), state.failMessage, 1000)
                            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.warning))
                            .show()
                    }
                    is CategoryState.PopUpScreen -> {
                        progressBar.gone()
                        cl.gone()
                        clFail.visible()
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
                        tvHello.gone()
                    }
                    is Resource.Success -> {
                        progressBar.gone()
                        tvHello.visible()
                        tvHello.text = "Hello ${state.data.name}"
                    }
                    is Resource.Fail -> {
                        progressBar.gone()
                        tvHello.gone()
                        Snackbar.make(requireView(), state.failMessage, 1000)
                            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.warning))
                            .show()
                    }
                    is Resource.Error -> {
                        progressBar.gone()
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
                        clFail.gone()
                    }

                    is AddToCartState.SuccessMessage -> {
                        progressBar.gone()
                        clFail.gone()
                        cl.visible()
                        Snackbar.make(requireView(), state.message, 1000)
                            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.success))
                            .show()
                    }

                    is AddToCartState.FailMessage -> {
                        progressBar.gone()
                        clFail.gone()
                        cl.visible()
                        Snackbar.make(requireView(), state.failMessage, 1000)
                            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.warning))
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

    private fun onProductClick(id: Int) {
        findNavController().navigate(HomeFragmentDirections.homeToDetail(id))
    }

    private fun onFavClick(product: ProductUI) {
        viewModel.setFavoriteState(product)
    }

    private fun onCartClick(productId: Int){
        viewModel.addToCart(productId)
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