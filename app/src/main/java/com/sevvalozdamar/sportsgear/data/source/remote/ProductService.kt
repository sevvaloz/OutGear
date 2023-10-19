package com.sevvalozdamar.sportsgear.data.source.remote

import com.sevvalozdamar.sportsgear.data.model.ProductResponse
import retrofit2.Call
import retrofit2.http.GET

interface ProductService {

    @GET("get_products.php")
    fun getProducts(): Call<ProductResponse>
}