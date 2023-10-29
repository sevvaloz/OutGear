package com.sevvalozdamar.sportsgear.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sevvalozdamar.sportsgear.data.model.Product
import com.sevvalozdamar.sportsgear.data.model.User
import com.sevvalozdamar.sportsgear.data.repository.FirebaseAuthenticator
import com.sevvalozdamar.sportsgear.data.repository.ProductRepository
import com.sevvalozdamar.sportsgear.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productRepository: ProductRepository,
private val firebaseAuthenticator: FirebaseAuthenticator
): ViewModel() {

    private var _products = MutableLiveData<Resource<List<Product>>>()
    val products: LiveData<Resource<List<Product>>> get() = _products

    private val _user = MutableLiveData<Resource<User>>()
    val user: LiveData<Resource<User>> = _user

    init{
        _products = productRepository.products
        viewModelScope.launch {
            _user.value = Resource.Success(firebaseAuthenticator.getCurrentUser())
        }
    }

    fun getProducts() = viewModelScope.launch {
        productRepository.getProducts()
    }

    fun signuot() = viewModelScope.launch {
        firebaseAuthenticator.signOut()
    }

}