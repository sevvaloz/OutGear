package com.sevvalozdamar.sportsgear.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sevvalozdamar.sportsgear.R
import com.sevvalozdamar.sportsgear.databinding.FragmentHomeBinding
import com.sevvalozdamar.sportsgear.utils.Firebase
import com.sevvalozdamar.sportsgear.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel by viewModels<HomeViewModel>()
    private val productAdapter = ProductAdapter(onProductClick = ::onProductClick)
    private val saleProductAdapter = ProductAdapter(onProductClick = ::onProductClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
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
        viewModel.products.observe(viewLifecycleOwner) { productList ->
            productAdapter.submitList(productList)
            saleProductAdapter.submitSaleProductList(productList)
        }
    }

    private fun onProductClick(id: Int){
        findNavController().navigate(HomeFragmentDirections.homeToDetail(id))
    }

    private fun signOut() {
        Firebase.auth.signOut()
    }

}