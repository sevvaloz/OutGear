package com.sevvalozdamar.sportsgear.ui.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sevvalozdamar.sportsgear.R
import com.sevvalozdamar.sportsgear.databinding.FragmentSearchBinding
import com.sevvalozdamar.sportsgear.utils.PopupHelper
import com.sevvalozdamar.sportsgear.utils.gone
import com.sevvalozdamar.sportsgear.utils.invisible
import com.sevvalozdamar.sportsgear.utils.viewBinding
import com.sevvalozdamar.sportsgear.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel by viewModels<SearchViewModel>()
    private val adapter =
        SearchAdapter(onProductClick = ::onProductClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvSearchProduct.adapter = adapter
            svSearchProduct.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if ((query?.length ?: 0) >= 3) {
                        viewModel.getSearchProduct(query!!)
                        observeData()
                    } else {
                        rvSearchProduct.gone()
                    }

                    return false
                }

                override fun onQueryTextChange(newQuery: String?): Boolean {
                    if (!newQuery.isNullOrEmpty() && newQuery.length >= 3) {
                        viewModel.getSearchProduct(newQuery)
                        observeData()
                    } else if (newQuery!!.length<3) {
                        rvSearchProduct.gone()
                    }
                    return false
                }
            })
        }

        observeData()
    }

    private fun observeData() {
        viewModel.searchState.observe(viewLifecycleOwner) { state ->
            binding.apply {
                when (state) {
                    SearchState.Loading -> {
                        progressBar.visible()
                        clFail.gone()
                        rvSearchProduct.invisible()
                    }

                    is SearchState.SuccessScreen -> {
                        progressBar.gone()
                        clFail.gone()
                        rvSearchProduct.visible()
                        adapter.submitList(state.products)
                    }

                    is SearchState.EmptyScreen -> {
                        progressBar.gone()
                        clFail.visible()
                        rvSearchProduct.invisible()
                        tvFail.text = state.failMessage
                    }

                    is SearchState.PopUpScreen -> {
                        progressBar.gone()
                        clFail.visible()
                        rvSearchProduct.invisible()
                        PopupHelper.showErrorPopup(requireContext(), state.errorMessage)
                    }
                }
            }
        }
    }

    private fun onProductClick(id: Int) {
        findNavController().navigate(SearchFragmentDirections.searchToDetail(id))
    }

}