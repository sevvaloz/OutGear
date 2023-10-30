package com.sevvalozdamar.sportsgear.data.source.remote

import com.sevvalozdamar.sportsgear.data.model.ProductDetailResponse
import com.sevvalozdamar.sportsgear.data.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {

    @GET("get_products.php")
    suspend fun getProducts(): Response<ProductResponse>

    @GET("get_product_detail.php")
    suspend fun getProductDetail(@Query("id") id: Int): Response<ProductDetailResponse>

}