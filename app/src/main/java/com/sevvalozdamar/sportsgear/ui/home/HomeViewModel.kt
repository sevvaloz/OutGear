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

    private var _addToCartState = MutableLiveData<AddToCartState>()
    val addToCartState: LiveData<AddToCartState> get() = _addToCartState

    private var _categoryState = MutableLiveData<CategoryState>()
    val categoryState: LiveData<CategoryState> get() = _categoryState

    private var _productByCategoryState = MutableLiveData<HomeState>()
    val productByCategoryState: LiveData<HomeState> get() = _productByCategoryState


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

    fun signOut() = viewModelScope.launch {
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

    fun addToCart(productId: Int) = viewModelScope.launch{
        _addToCartState.value =  AddToCartState.Loading
        _addToCartState.value = when (val result = productRepository.addToCart(productId)) {
            Resource.Loading -> AddToCartState.Loading
            is Resource.Success -> AddToCartState.SuccessMessage(result.data)
            is Resource.Fail -> AddToCartState.FailMessage(result.failMessage)
            is Resource.Error -> AddToCartState.PopUpScreen(result.errorMessage)
        }
    }

    fun getCategories() = viewModelScope.launch {
        _categoryState.value = CategoryState.Loading
        _categoryState.value = when (val result = productRepository.getCategories()) {
            Resource.Loading -> CategoryState.Loading
            is Resource.Success -> CategoryState.SuccessScreen(result.data)
            is Resource.Fail -> CategoryState.FailMessage(result.failMessage)
            is Resource.Error -> CategoryState.PopUpScreen(result.errorMessage)
        }
    }

    fun getProductsByCategory(category: String) = viewModelScope.launch{
        _productByCategoryState.value = HomeState.Loading
        _productByCategoryState.value = when (val result = productRepository.getProductsByCategory(category)) {
            Resource.Loading ->  HomeState.Loading
            is Resource.Success -> HomeState.SuccessScreen(result.data)
            is Resource.Fail -> HomeState.EmptyScreen(result.failMessage)
            is Resource.Error -> HomeState.PopUpScreen(result.errorMessage)
        }
    }
}

sealed interface HomeState {
    data object Loading : HomeState
    data class SuccessScreen(val products: List<ProductUI>) : HomeState
    data class EmptyScreen(val failMessage: String) : HomeState
    data class PopUpScreen(val errorMessage: String) : HomeState
}

sealed interface AddToCartState {
    data object Loading : AddToCartState
    data class SuccessMessage(val message: String) : AddToCartState
    data class FailMessage(val failMessage: String) : AddToCartState
    data class PopUpScreen(val errorMessage: String) : AddToCartState
}

sealed interface CategoryState {
    data object Loading : CategoryState
    data class SuccessScreen(val categories: List<String>) : CategoryState
    data class FailMessage(val failMessage: String) : CategoryState
    data class PopUpScreen(val errorMessage: String) : CategoryState
}