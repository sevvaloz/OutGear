package com.sevvalozdamar.sportsgear.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sevvalozdamar.sportsgear.data.model.ProductUI
import com.sevvalozdamar.sportsgear.data.repository.ProductRepository
import com.sevvalozdamar.sportsgear.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private var _favoritesState = MutableLiveData<FavoritesState>()
    val favoritesState: LiveData<FavoritesState> get() = _favoritesState

    fun getFavorites() = viewModelScope.launch {
        _favoritesState.value = FavoritesState.Loading
        _favoritesState.value = when (val result = productRepository.getFavorites()) {
            Resource.Loading -> FavoritesState.Loading
            is Resource.Success -> FavoritesState.SuccessScreen(result.data)
            is Resource.Fail -> FavoritesState.EmptyScreen(result.failMessage)
            is Resource.Error -> FavoritesState.PopUpScreen(result.errorMessage)
        }
    }

    fun deleteFromFavorites(product: ProductUI) = viewModelScope.launch {
        productRepository.deleteFromFavorites(product)
        getFavorites()
    }

    fun deleteAllFavorites(products: List<ProductUI>) = viewModelScope.launch {
        productRepository.deleteAllFavorites(products)
        getFavorites()
    }

}

sealed interface FavoritesState {
    data object Loading : FavoritesState
    data class SuccessScreen(val products: List<ProductUI>) : FavoritesState
    data class PopUpScreen(val errorMessage: String) : FavoritesState
    data class EmptyScreen(val failMessage: String) : FavoritesState
}