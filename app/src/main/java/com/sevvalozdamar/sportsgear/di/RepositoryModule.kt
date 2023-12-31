package com.sevvalozdamar.sportsgear.di

import com.sevvalozdamar.sportsgear.data.repository.FirebaseAuthenticator
import com.sevvalozdamar.sportsgear.data.repository.ProductRepository
import com.sevvalozdamar.sportsgear.data.source.local.ProductDao
import com.sevvalozdamar.sportsgear.data.source.remote.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideProductRepository(productService: ProductService, productDao: ProductDao, firebaseAuthenticator: FirebaseAuthenticator) =
        ProductRepository(productService, productDao, firebaseAuthenticator)
}