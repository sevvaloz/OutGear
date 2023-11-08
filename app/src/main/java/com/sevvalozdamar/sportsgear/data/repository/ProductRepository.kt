package com.sevvalozdamar.sportsgear.data.repository

import com.sevvalozdamar.sportsgear.data.mapper.mapToFavProductEntity
import com.sevvalozdamar.sportsgear.data.mapper.mapToProductEntity
import com.sevvalozdamar.sportsgear.data.mapper.mapToProductUI
import com.sevvalozdamar.sportsgear.data.model.AddToCartRequest
import com.sevvalozdamar.sportsgear.data.model.ClearCartRequest
import com.sevvalozdamar.sportsgear.data.model.DeleteFromCartRequest
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

    suspend fun addToFavorites(product: ProductUI) = withContext(Dispatchers.IO) {
        productDao.addToFavorites(product.mapToFavProductEntity(firebaseAuthenticator.getFirebaseUserUid()))
    }

    suspend fun deleteFromFavorites(product: ProductUI) = withContext(Dispatchers.IO) {
        productDao.deleteFromFavorites(product.mapToFavProductEntity(firebaseAuthenticator.getFirebaseUserUid()))
    }

    suspend fun deleteAllFavorites(products: List<ProductUI>) = withContext(Dispatchers.IO) {
        productDao.deleteAllFavorites(products.mapToProductEntity(firebaseAuthenticator.getFirebaseUserUid()))
    }

    suspend fun getFavorites(): Resource<List<ProductUI>> = withContext(Dispatchers.IO) {
        try {
            val favorites = productDao.getFavorites(firebaseAuthenticator.getFirebaseUserUid())

            if (favorites.isNotEmpty()) {
                Resource.Success(favorites.mapToProductUI())
            } else {
                Resource.Fail("There are no products in your favorites")
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun addToCart(productId: Int): Resource<String> = withContext(Dispatchers.IO) {
        try {
            val request = AddToCartRequest(firebaseAuthenticator.getFirebaseUserUid(), productId)
            val response = productService.addToCart(request)

            if (response.status == 200 && response.message != null) {
                //if product is successfully added to cart
                Resource.Success(response.message)
            } else {
                //if product is already in the cart
                Resource.Fail(response.message.orEmpty())
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun getCartProducts(): Resource<List<ProductUI>> = withContext(Dispatchers.IO) {
        try {
            val response = productService.getCartProducts(firebaseAuthenticator.getFirebaseUserUid()).body()
            val favorites = productDao.getFavoriteIds(firebaseAuthenticator.getFirebaseUserUid())

            if ((response?.status == 200) && (response.products != null)) {
                Resource.Success(response.products.mapToProductUI(favorites))
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun deleteFromCart(productId: Int): Resource<String> = withContext(Dispatchers.IO) {
        try {
            val request = DeleteFromCartRequest(firebaseAuthenticator.getFirebaseUserUid(), productId)
            val response = productService.deleteFromCart(request)

            if (response.status == 200) {
                //if product is successfully deleted from cart
                Resource.Success(response.message.orEmpty())
            } else {
                //if product not found in the cart
                Resource.Fail(response.message.orEmpty())
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun clearCart(): Resource<String> = withContext(Dispatchers.IO) {
        try {
            val request = ClearCartRequest(firebaseAuthenticator.getFirebaseUserUid())
            val response = productService.clearCart(request)

            if (response.status == 200) {
                //if cart is successfully cleared
                Resource.Success(response.message.orEmpty())
            } else {
                //if cart is not cleaned
                Resource.Fail(response.message.orEmpty())
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun getSearchProduct(query: String): Resource<List<ProductUI>> = withContext(Dispatchers.IO) {
        try {
            val response = productService.searchProduct(query).body()
            val favorites = productDao.getFavoriteIds(firebaseAuthenticator.getFirebaseUserUid())

            if ((response?.status == 200) && (response.products != null)) {
                Resource.Success(response.products.mapToProductUI(favorites))
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun getCategories(): Resource<List<String>> = withContext(Dispatchers.IO) {
        try {
            val response = productService.getCategories().body()

            if (response?.status == 200 && (response.categories != null)) {
                Resource.Success(response.categories)
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun getProductsByCategory(category: String): Resource<List<ProductUI>> = withContext(Dispatchers.IO) {
        try {
            val response = productService.getProductsByCategory(category).body()
            val favorites = productDao.getFavoriteIds(firebaseAuthenticator.getFirebaseUserUid())

            if ((response?.status == 200) && (response.products != null)) {
                Resource.Success(response.products.mapToProductUI(favorites))
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }

}