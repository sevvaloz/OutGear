package com.sevvalozdamar.sportsgear.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sevvalozdamar.sportsgear.data.model.Product
import com.sevvalozdamar.sportsgear.data.model.ProductResponse
import com.sevvalozdamar.sportsgear.data.source.remote.ProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository(private val productService: ProductService) {

    var productsLiveData = MutableLiveData<List<Product>>()

    fun getProducts(){
        productService.getProducts().enqueue(object  : Callback<ProductResponse> {

            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                val result = response.body()
                if(result?.status == 200){
                    productsLiveData.value = result.products.orEmpty()
                } else {
                    Log.e("GETPRODUCTS", "code 400")
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.e("GETPRODUCTS", t.message.orEmpty())
            }

        })
    }
}