package com.sevvalozdamar.sportsgear.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sevvalozdamar.sportsgear.data.model.Product
import com.sevvalozdamar.sportsgear.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private var _productDetail = MutableLiveData<Product>()
    val productDetail: LiveData<Product> get() = _productDetail

    init {
        _productDetail = productRepository.productDetailLiveData
    }

    fun getProductDetail(id: Int){
        productRepository.getProductDetail(id)
    }

}