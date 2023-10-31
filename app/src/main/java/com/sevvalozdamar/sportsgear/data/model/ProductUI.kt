package com.sevvalozdamar.sportsgear.data.model

//this data class created for blocking the nullable values while fetching the data from service to UI
data class ProductUI(
    val category: String,
    val count: Int,
    val description: String,
    val id: Int,
    val imageOne: String,
    val imageThree: String,
    val imageTwo: String,
    val price: Double,
    val rate: Double,
    val salePrice: Double,
    val saleState: Boolean,
    val title: String,
    val isFav: Boolean = false
)
