package com.sevvalozdamar.sportsgear.data.repository

import com.sevvalozdamar.sportsgear.data.mapper.mapToFavProductEntity
import com.sevvalozdamar.sportsgear.data.mapper.mapToProductEntity
import com.sevvalozdamar.sportsgear.data.mapper.mapToProductUI
import com.sevvalozdamar.sportsgear.data.model.ProductUI
import com.sevvalozdamar.sportsgear.data.source.local.ProductDao
import com.sevvalozdamar.sportsgear.data.source.remote.ProductService
import com.sevvalozdamar.sportsgear.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(
    private val productService: ProductService,
    private val productDao: ProductDao,
    private val firebaseAuthenticator: FirebaseAuthenticator
) {

    suspend fun getProducts(): Resource<List<ProductUI>> = withContext(Dispatchers.IO) {
        try {
            val response = productService.getProducts().body()
            val favorites = productDao.getFavoriteIds(firebaseAuthenticator.getFirebaseUserUid())

            if (response?.status == 200) {
                Resource.Success(response.products.orEmpty().mapToProductUI(favorites))
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun getProductDetail(id: Int): Resource<ProductUI> = withContext(Dispatchers.IO) {
        try {
            val response = productService.getProductDetail(id).body()
            val favorites = productDao.getFavoriteIds(firebaseAuthenticator.getFirebaseUserUid())

            if ((response?.status == 200) && (response.product != null)) {
                Resource.Success(response.product.mapToProductUI(favorites))
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun addToFavorites(product: ProductUI) {
        productDao.addToFavorites(product.mapToFavProductEntity(firebaseAuthenticator.getFirebaseUserUid()))
    }

    suspend fun deleteFromFavorites(product: ProductUI) {
        productDao.deleteFromFavorites(product.mapToFavProductEntity(firebaseAuthenticator.getFirebaseUserUid()))
    }

    suspend fun deleteAllFavorites(products: List<ProductUI>) {
        productDao.deleteAllFavorites(products.mapToProductEntity(firebaseAuthenticator.getFirebaseUserUid()))
    }

    suspend fun getFavorites(): Resource<List<ProductUI>> = withContext(Dispatchers.IO) {
        try {
            val favorites = productDao.getFavorites(firebaseAuthenticator.getFirebaseUserUid())

            if (favorites.isNotEmpty()) {
                Resource.Success(favorites.mapToProductUI())
            } else {
                Resource.Fail("This place looks empty.. Start with adding your favorite products.")
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }

}