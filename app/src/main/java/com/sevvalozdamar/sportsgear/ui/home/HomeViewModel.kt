package com.sevvalozdamar.sportsgear.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sevvalozdamar.sportsgear.data.model.Product
import com.sevvalozdamar.sportsgear.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productRepository: ProductRepository): ViewModel() {

    private var _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    init {
        _products = productRepository.productsLiveData
    }

    fun getProducts(){
        productRepository.getProducts()
    }

}