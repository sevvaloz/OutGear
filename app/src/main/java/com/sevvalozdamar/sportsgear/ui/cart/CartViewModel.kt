package com.sevvalozdamar.sportsgear.ui.cart

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
class CartViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private var _cartState = MutableLiveData<CartState>()
    val cartState: LiveData<CartState> get() = _cartState

    private var _deleteFromCartState = MutableLiveData<DeleteCartState>()
    val deleteFromCartState: LiveData<DeleteCartState> get() = _deleteFromCartState

    private var _clearCartState = MutableLiveData<DeleteCartState>()
    val clearCartState: LiveData<DeleteCartState> get() = _clearCartState


    fun getCartProducts() = viewModelScope.launch {
        _cartState.value = CartState.Loading
        _cartState.value = when (val result = productRepository.getCartProducts()) {
            Resource.Loading -> CartState.Loading
            is Resource.Success -> CartState.SuccessScreen(result.data)
            is Resource.Fail -> CartState.EmptyScreen(result.failMessage)
            is Resource.Error -> CartState.PopUpScreen(result.errorMessage)
        }
    }

    fun deleteFromCart(productId: Int) = viewModelScope.launch {
        _deleteFromCartState.value = when (val result = productRepository.deleteFromCart(productId)) {
            Resource.Loading -> DeleteCartState.Loading
            is Resource.Success -> DeleteCartState.SuccessMessage(result.data)
            is Resource.Fail -> DeleteCartState.FailMessage(result.failMessage)
            is Resource.Error -> DeleteCartState.PopUpScreen(result.errorMessage)
        }
        getCartProducts()
    }

    fun clearCart() = viewModelScope.launch {
        _clearCartState.value = when (val result = productRepository.clearCart()) {
            Resource.Loading -> DeleteCartState.Loading
            is Resource.Success -> DeleteCartState.SuccessMessage(result.data)
            is Resource.Fail -> DeleteCartState.FailMessage(result.failMessage)
            is Resource.Error -> DeleteCartState.PopUpScreen(result.errorMessage)
        }
        getCartProducts()
    }
}

sealed interface CartState {
    data object Loading : CartState
    data class SuccessScreen(val products: List<ProductUI>) : CartState
    data class PopUpScreen(val errorMessage: String) : CartState
    data class EmptyScreen(val failMessage: String) : CartState
}

sealed interface DeleteCartState {
    data object Loading : DeleteCartState
    data class SuccessMessage(val message: String) : DeleteCartState
    data class FailMessage(val failMessage: String) : DeleteCartState
    data class PopUpScreen(val errorMessage: String) : DeleteCartState
}