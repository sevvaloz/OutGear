package com.sevvalozdamar.sportsgear.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sevvalozdamar.sportsgear.data.model.FavProductEntity

@Database(entities = [FavProductEntity::class], version = 1)
abstract class ProductRoomDB: RoomDatabase() {

    abstract fun productDao(): ProductDao
}