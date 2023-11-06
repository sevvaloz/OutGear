package com.sevvalozdamar.sportsgear.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sevvalozdamar.sportsgear.R
import com.sevvalozdamar.sportsgear.databinding.FragmentCartBinding
import com.sevvalozdamar.sportsgear.ui.home.AddToCartState
import com.sevvalozdamar.sportsgear.utils.PopupHelper
import com.sevvalozdamar.sportsgear.utils.gone
import com.sevvalozdamar.sportsgear.utils.viewBinding
import com.sevvalozdamar.sportsgear.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private val binding by viewBinding(FragmentCartBinding::bind)
    private val viewModel by viewModels<CartViewModel>()
    private val adapter =
        CartAdapter(onProductClick = ::onProductClick, onDeleteClick = ::onDeleteClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvCartProduct.adapter = adapter

            ivClearCart.setOnClickListener {
                PopupHelper.showClearCartPopup(requireContext(), onYesClicked = {
                    viewModel.clearCart()
                })
            }
        }

        viewModel.getCartProducts()
        observeData()

    }

    private fun observeData() {
        viewModel.cartState.observe(viewLifecycleOwner) { state ->
            binding.apply {
                when (state) {
                    CartState.Loading -> {
                        progressBar.visible()
                        rvCartProduct.gone()
                        clFail.gone()
                        ivClearCart.gone()
                    }

                    is CartState.SuccessScreen -> {
                        progressBar.gone()
                        clFail.gone()
                        rvCartProduct.visible()
                        ivClearCart.visible()
                        adapter.submitList(state.products)
                    }

                    is CartState.EmptyScreen -> {
                        progressBar.gone()
                        rvCartProduct.gone()
                        ivClearCart.gone()
                        clFail.visible()
                        tvFail.text = state.failMessage
                    }

                    is CartState.PopUpScreen -> {
                        progressBar.gone()
                        rvCartProduct.gone()
                        ivClearCart.gone()
                        clFail.visible()
                        PopupHelper.showErrorPopup(requireContext(), state.errorMessage)
                    }
                }
            }
        }

        viewModel.deleteFromCartState.observe(viewLifecycleOwner){ state ->
            binding.apply {
                when(state){
                    DeleteCartState.Loading -> {
                        progressBar.visible()
                        clFail.gone()
                    }

                    is DeleteCartState.SuccessMessage -> {
                        progressBar.gone()
                        clFail.gone()
                        Snackbar.make(requireView(), state.message, 1000).show()
                    }

                    is DeleteCartState.FailMessage -> {
                        progressBar.gone()
                        clFail.gone()
                        Snackbar.make(requireView(), state.failMessage, 1000).show()
                    }

                    is DeleteCartState.PopUpScreen -> {
                        progressBar.gone()
                        clFail.visible()
                        PopupHelper.showErrorPopup(requireContext(), state.errorMessage)
                    }
                }
            }
        }

        viewModel.clearCartState.observe(viewLifecycleOwner){ state ->
            binding.apply {
                when(state){
                    DeleteCartState.Loading -> {
                        progressBar.visible()
                        clFail.gone()
                    }

                    is DeleteCartState.SuccessMessage -> {
                        progressBar.gone()
                        clFail.gone()
                        Snackbar.make(requireView(), state.message, 1000).show()
                    }

                    is DeleteCartState.FailMessage -> {
                        progressBar.gone()
                        clFail.gone()
                        Snackbar.make(requireView(), state.failMessage, 1000).show()
                    }

                    is DeleteCartState.PopUpScreen -> {
                        progressBar.gone()
                        clFail.visible()
                        PopupHelper.showErrorPopup(requireContext(), state.errorMessage)
                    }
                }
            }
        }
    }


    private fun onProductClick(id: Int) {
        findNavController().navigate(CartFragmentDirections.cartToDetail(id))
    }

    private fun onDeleteClick(productId: Int) {
        viewModel.deleteFromCart(productId)
    }

}