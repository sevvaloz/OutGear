package com.sevvalozdamar.sportsgear.data.repository

import androidx.lifecycle.MutableLiveData
import com.sevvalozdamar.sportsgear.data.model.Product
import com.sevvalozdamar.sportsgear.data.source.remote.ProductService
import com.sevvalozdamar.sportsgear.utils.Resource

class ProductRepository(private val productService: ProductService) {

    var products = MutableLiveData<Resource<List<Product>>>()
    var productDetail= MutableLiveData<Resource<Product>>()

    suspend fun getProducts() {
        products.value = Resource.Loading
        try {
            val response = productService.getProducts().body()
            if (response?.status == 200){
                products.value = Resource.Success(response.products.orEmpty())
            } else {
                products.value = Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception){
            products.value = Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun getProductDetail(id: Int) {
         productDetail.value = Resource.Loading
        try {
            val response = productService.getProductDetail(id).body()
            if (response?.status == 200 && response.product != null) {
                productDetail.value = Resource.Success(response.product)
            } else {
                products.value = Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception){
            products.value = Resource.Error(e.message.orEmpty())
        }
    }

}