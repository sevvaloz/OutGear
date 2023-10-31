package com.sevvalozdamar.sportsgear.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sevvalozdamar.sportsgear.data.model.FavProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM fav_products")
    suspend fun getFavorites(): List<FavProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(favProduct: FavProductEntity)

    @Delete
    suspend fun deleteFromFavorites(favProduct: FavProductEntity)

    @Delete
    suspend fun deleteAllFavorites(favProducts: List<FavProductEntity>)

    @Query("SELECT productId FROM fav_products")
    suspend fun getProductIds(): List<Int>
}