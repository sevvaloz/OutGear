package com.sevvalozdamar.sportsgear.ui.detail

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
class DetailViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private var _detailState = MutableLiveData<DetailState>()
    val detailState: LiveData<DetailState> get() = _detailState

    fun getProductDetail(id: Int) = viewModelScope.launch{
        _detailState.value = DetailState.Loading
        _detailState.value = when (val result = productRepository.getProductDetail(id)) {
            Resource.Loading ->  DetailState.Loading
            is Resource.Success -> DetailState.SuccessScreen(result.data)
            is Resource.Fail -> DetailState.EmptyScreen(result.failMessage)
            is Resource.Error -> DetailState.PopUpScreen(result.errorMessage)
        }
    }

    fun setFavoriteState(product: ProductUI) = viewModelScope.launch {
        if (product.isFav) {
            productRepository.deleteFromFavorites(product)
        } else {
            productRepository.addToFavorites(product)
        }
        getProductDetail(product.id)
    }

}

sealed interface DetailState {
    data object Loading : DetailState
    data class SuccessScreen(val product: ProductUI) : DetailState
    data class PopUpScreen(val errorMessage: String) : DetailState
    data class EmptyScreen(val failMessage: String) : DetailState
}