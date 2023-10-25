package com.sevvalozdamar.sportsgear.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sevvalozdamar.sportsgear.R
import com.sevvalozdamar.sportsgear.databinding.FragmentHomeBinding
import com.sevvalozdamar.sportsgear.utils.Firebase
import com.sevvalozdamar.sportsgear.utils.Resource
import com.sevvalozdamar.sportsgear.utils.gone
import com.sevvalozdamar.sportsgear.utils.viewBinding
import com.sevvalozdamar.sportsgear.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel by viewModels<HomeViewModel>()
    private val productAdapter = ProductAdapter(onProductClick = ::onProductClick)
    private val saleProductAdapter = ProductAdapter(onProductClick = ::onProductClick)

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
        viewModel.products.observe(viewLifecycleOwner) {
            binding.apply {
                when (it) {
                    Resource.Loading -> {
                        binding.progressBar.visible()
                        cl.gone()
                    }

                    is Resource.Success -> {
                        binding.progressBar.gone()
                        cl.visible()
                        productAdapter.submitList(it.data)
                        saleProductAdapter.submitSaleProductList(it.data)
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

    private fun onProductClick(id: Int) {
        findNavController().navigate(HomeFragmentDirections.homeToDetail(id))
    }

    private fun signOut() {
        Firebase.auth.signOut()
    }

}