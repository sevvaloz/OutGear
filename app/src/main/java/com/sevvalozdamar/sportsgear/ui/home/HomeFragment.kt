package com.sevvalozdamar.sportsgear.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sevvalozdamar.sportsgear.R
import com.sevvalozdamar.sportsgear.databinding.FragmentHomeBinding
import com.sevvalozdamar.sportsgear.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel by viewModels<HomeViewModel>()
    private val adapter = ProductAdapter(onProductClick = ::onProductClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            rvProduct.adapter = adapter
        }
        observeData()
    }

    private fun observeData() {
        viewModel.products.observe(viewLifecycleOwner) { productList ->
            adapter.submitList(productList)
        }
    }

    private fun onProductClick(id: Int){
        findNavController().navigate(HomeFragmentDirections.homeToDetail(id))
    }

}