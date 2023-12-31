package com.sevvalozdamar.sportsgear.data.source.remote

import com.sevvalozdamar.sportsgear.data.model.AddToCartRequest
import com.sevvalozdamar.sportsgear.data.model.BaseResponse
import com.sevvalozdamar.sportsgear.data.model.CartProductResponse
import com.sevvalozdamar.sportsgear.data.model.CategoryResponse
import com.sevvalozdamar.sportsgear.data.model.ClearCartRequest
import com.sevvalozdamar.sportsgear.data.model.DeleteFromCartRequest
import com.sevvalozdamar.sportsgear.data.model.ProductByCategoryResponse
import com.sevvalozdamar.sportsgear.data.model.ProductDetailResponse
import com.sevvalozdamar.sportsgear.data.model.ProductResponse
import com.sevvalozdamar.sportsgear.data.model.SearchProductResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductService {

    @GET("get_products.php")
    suspend fun getProducts(): Response<ProductResponse>

    @GET("get_product_detail.php")
    suspend fun getProductDetail(@Query("id") id: Int): Response<ProductDetailResponse>

    @POST("add_to_cart.php")
    suspend fun addToCart(@Body addToCartRequest: AddToCartRequest): BaseResponse

    @GET("get_cart_products.php")
    suspend fun getCartProducts(@Query("userId") userId: String): Response<CartProductResponse>

    @POST("delete_from_cart.php")
    suspend fun deleteFromCart(@Body deleteFromCartRequest: DeleteFromCartRequest): BaseResponse

    @POST("clear_cart.php")
    suspend fun clearCart(@Body clearCartRequest: ClearCartRequest): BaseResponse

    @GET("search_product.php")
    suspend fun searchProduct(@Query("query") query: String): Response<SearchProductResponse>

    @GET("get_categories.php")
    suspend fun getCategories(): Response<CategoryResponse>

    @GET("get_products_by_category.php")
    suspend fun getProductsByCategory(@Query("category") category: String): Response<ProductByCategoryResponse>

}