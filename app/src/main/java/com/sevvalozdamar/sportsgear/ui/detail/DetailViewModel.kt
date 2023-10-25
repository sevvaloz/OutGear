package com.sevvalozdamar.sportsgear.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sevvalozdamar.sportsgear.data.model.Product
import com.sevvalozdamar.sportsgear.data.repository.ProductRepository
import com.sevvalozdamar.sportsgear.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private var _productDetail = MutableLiveData<Resource<Product>>()
    val productDetail: LiveData<Resource<Product>> get() = _productDetail

    init {
        _productDetail = productRepository.productDetail
    }

    fun getProductDetail(id: Int) = viewModelScope.launch{
        productRepository.getProductDetail(id)
    }

}