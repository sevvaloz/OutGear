package com.sevvalozdamar.sportsgear.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sevvalozdamar.sportsgear.R
import com.sevvalozdamar.sportsgear.data.model.ProductUI
import com.sevvalozdamar.sportsgear.databinding.FragmentFavoritesBinding
import com.sevvalozdamar.sportsgear.utils.PopupHelper
import com.sevvalozdamar.sportsgear.utils.gone
import com.sevvalozdamar.sportsgear.utils.viewBinding
import com.sevvalozdamar.sportsgear.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val binding by viewBinding(FragmentFavoritesBinding::bind)
    private val viewModel by viewModels<FavoritesViewModel>()
    private val adapter =
        FavoritesAdapter(onProductClick = ::onProductClick, onDeleteClick = ::onDeleteClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvFavProduct.adapter = adapter
        }

        viewModel.getFavorites()
        observeData()
    }

    private fun observeData() {
        viewModel.favoritesState.observe(viewLifecycleOwner) { state ->
            binding.apply {
                when (state) {
                    FavoritesState.Loading -> {
                        binding.progressBar.visible()
                        rvFavProduct.gone()
                        clFail.gone()
                        ivDeleteAllFavorites.gone()
                    }

                    is FavoritesState.SuccessScreen -> {
                        binding.progressBar.gone()
                        clFail.gone()
                        rvFavProduct.visible()
                        ivDeleteAllFavorites.visible()
                        adapter.submitList(state.products)

                        ivDeleteAllFavorites.setOnClickListener {
                            PopupHelper.showDeleteFavPopup(requireContext(), onYesClicked = {
                                viewModel.deleteAllFavorites(state.products)
                                Snackbar.make(
                                    requireView(),
                                    "Favorites cleared",
                                    1000
                                )
                                    .setBackgroundTint(
                                        ContextCompat.getColor(
                                            requireContext(),
                                            R.color.success
                                        )
                                    )
                                    .show()
                            })
                        }
                    }

                    is FavoritesState.EmptyScreen -> {
                        binding.progressBar.gone()
                        rvFavProduct.gone()
                        ivDeleteAllFavorites.gone()
                        clFail.visible()
                        tvFail.text = state.failMessage
                    }

                    is FavoritesState.PopUpScreen -> {
                        binding.progressBar.gone()
                        rvFavProduct.gone()
                        ivDeleteAllFavorites.gone()
                        clFail.visible()
                        PopupHelper.showErrorPopup(requireContext(), state.errorMessage)
                    }
                }
            }
        }
    }

    private fun onProductClick(id: Int) {
        findNavController().navigate(FavoritesFragmentDirections.favoritesToDetail(id))
    }

    private fun onDeleteClick(product: ProductUI) {
        viewModel.deleteFromFavorites(product)
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
    }

}