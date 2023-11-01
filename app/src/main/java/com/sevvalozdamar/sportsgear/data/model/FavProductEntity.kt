package com.sevvalozdamar.sportsgear.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_products", primaryKeys = ["productId", "userId"])
data class FavProductEntity(

    @ColumnInfo(name = "productId")
    val productId: Int,

    @ColumnInfo(name = "category")
    val category: String?,

    @ColumnInfo(name = "count")
    val count: Int?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "imageOne")
    val imageOne: String?,

    @ColumnInfo(name = "imageTwo")
    val imageTwo: String?,

    @ColumnInfo(name = "imageThree")
    val imageThree: String?,

    @ColumnInfo(name = "price")
    val price: Double?,

    @ColumnInfo(name = "rate")
    val rate: Double?,

    @ColumnInfo(name = "salePrice")
    val salePrice: Double?,

    @ColumnInfo(name = "saleState")
    val saleState: Boolean?,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "userId")
    val userId: String
)