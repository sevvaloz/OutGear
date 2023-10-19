package com.sevvalozdamar.sportsgear.data.model

data class ProductResponse(
    val message: String?,
    val products: List<Product>?,
    val status: Int?
)