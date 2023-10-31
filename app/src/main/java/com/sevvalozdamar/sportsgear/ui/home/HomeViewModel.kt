package com.sevvalozdamar.sportsgear.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sevvalozdamar.sportsgear.data.model.ProductUI
import com.sevvalozdamar.sportsgear.data.model.User
import com.sevvalozdamar.sportsgear.data.repository.FirebaseAuthenticator
import com.sevvalozdamar.sportsgear.data.repository.ProductRepository
import com.sevvalozdamar.sportsgear.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val firebaseAuthenticator: FirebaseAuthenticator
) : ViewModel() {

    private var _homeState = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState> get() = _homeState

    private val _user = MutableLiveData<Resource<User>>()
    val user: LiveData<Resource<User>> = _user

    init {
        viewModelScope.launch {
            _user.value = Resource.Success(firebaseAuthenticator.getCurrentUser())
        }
    }

    fun getProducts() = viewModelScope.launch {
        _homeState.value = HomeState.Loading
        _homeState.value = when (val result = productRepository.getProducts()) {
            Resource.Loading -> HomeState.Loading
            is Resource.Success -> HomeState.SuccessScreen(result.data)
            is Resource.Fail -> HomeState.EmptyScreen(result.failMessage)
            is Resource.Error -> HomeState.PopUpScreen(result.errorMessage)
        }
    }

    fun signuot() = viewModelScope.launch {
        firebaseAuthenticator.signOut()
    }

    fun setFavoriteState(product: ProductUI) = viewModelScope.launch {
        if (product.isFav) {
            productRepository.deleteFromFavorites(product)
        } else {
            productRepository.addToFavorites(product)
        }
        getProducts()
    }

}

sealed interface HomeState {
    data object Loading : HomeState
    data class SuccessScreen(val products: List<ProductUI>) : HomeState
    data class PopUpScreen(val errorMessage: String) : HomeState
    data class EmptyScreen(val failMessage: String) : HomeState
}