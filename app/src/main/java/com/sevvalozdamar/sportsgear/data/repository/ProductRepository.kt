package com.sevvalozdamar.sportsgear.data.repository

import com.sevvalozdamar.sportsgear.data.mapper.mapToProductUI
import com.sevvalozdamar.sportsgear.data.model.ProductUI
import com.sevvalozdamar.sportsgear.data.source.remote.ProductService
import com.sevvalozdamar.sportsgear.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(private val productService: ProductService) {

    suspend fun getProducts(): Resource<List<ProductUI>> = withContext(Dispatchers.IO) {
        try {
            val response = productService.getProducts().body()

            if (response?.status == 200) {
                Resource.Success(response.products.orEmpty().mapToProductUI())
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun getProductDetail(id: Int): Resource<ProductUI> = withContext(Dispatchers.IO) {
        try {
            val response = productService.getProductDetail(id).body()

            if ((response?.status == 200) && (response.product != null)) {
                Resource.Success(response.product.mapToProductUI())
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }

}