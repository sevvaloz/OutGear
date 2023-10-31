package com.sevvalozdamar.sportsgear.di

import android.content.Context
import androidx.room.Room
import com.sevvalozdamar.sportsgear.data.source.local.ProductRoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDBModule {

    @Singleton
    @Provides
    fun provideProductRoomDB(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ProductRoomDB::class.java, "products").build()

    @Singleton
    @Provides
    fun provideDao(roomDB: ProductRoomDB) = roomDB.productDao()
}