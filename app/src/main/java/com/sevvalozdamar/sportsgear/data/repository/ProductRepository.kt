package com.sevvalozdamar.sportsgear.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sevvalozdamar.sportsgear.data.model.Product
import com.sevvalozdamar.sportsgear.data.model.ProductDetailResponse
import com.sevvalozdamar.sportsgear.data.source.remote.ProductService
import com.sevvalozdamar.sportsgear.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        /*productService.getProducts().enqueue(object : Callback<ProductResponse> {

            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                val result = response.body()
                if (result?.status == 200) {
                    products.value = result.products.orEmpty()
                } else {
                    Log.e("GET-PRODUCTS", "code 400")
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e("GET-PRODUCTS", t.message.orEmpty())
            }

        })*/
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

        /*productService.getProductDetail(id).enqueue(object : Callback<ProductDetailResponse> {
            override fun onResponse(
                call: Call<ProductDetailResponse>,
                response: Response<ProductDetailResponse>
            ) {
                val result = response.body()
                if ((result?.status == 200) && (result.product != null)) {
                    productDetailLiveData.value = result.product
                } else {
                    Log.e("GET-PRODUCT-DETAIL", "code 400")
                }
            }

            override fun onFailure(call: Call<ProductDetailResponse>, t: Throwable) {
                Log.e("GET-PRODUCT-DETAIL", t.message.orEmpty())
            }

        })*/
    }

}