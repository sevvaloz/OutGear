package com.sevvalozdamar.sportsgear.data.mapper

import com.sevvalozdamar.sportsgear.data.model.FavProductEntity
import com.sevvalozdamar.sportsgear.data.model.Product
import com.sevvalozdamar.sportsgear.data.model.ProductUI

fun Product.mapToProductUI(favorites: List<Int>) =
    ProductUI(
        category = this.category.orEmpty(),
        count = this.count ?: 0,
        description = this.description.orEmpty(),
        id = this.id ?: 1,
        imageOne = this.imageOne.orEmpty(),
        imageThree = this.imageTwo.orEmpty(),
        imageTwo = this.imageThree.orEmpty(),
        price = this.price ?: 0.0,
        rate = this.rate ?: 0.0,
        salePrice = this.salePrice ?: 0.0,
        saleState = this.saleState ?: false,
        title = this.title.orEmpty(),
        isFav = favorites.contains(this.id)
    )

fun List<Product>.mapToProductUI(favorites: List<Int>) =
    map {
        ProductUI(
            category = it.category.orEmpty(),
            count = it.count ?: 0,
            description = it.description.orEmpty(),
            id = it.id ?: 1,
            imageOne = it.imageOne.orEmpty(),
            imageThree = it.imageTwo.orEmpty(),
            imageTwo = it.imageThree.orEmpty(),
            price = it.price ?: 0.0,
            rate = it.rate ?: 0.0,
            salePrice = it.salePrice ?: 0.0,
            saleState = it.saleState ?: false,
            title = it.title.orEmpty(),
            isFav = favorites.contains(it.id)
        )
    }

fun ProductUI.mapToFavProductEntity() =
    FavProductEntity(
        category = this.category,
        count = this.count,
        description = this.description,
        productId = this.id,
        imageOne = this.imageOne,
        imageThree = this.imageTwo,
        imageTwo = this.imageThree,
        price = this.price,
        rate = this.rate,
        salePrice = this.salePrice,
        saleState = this.saleState,
        title = this.title
    )

fun List<FavProductEntity>.mapToProductUI() =
    map {
        ProductUI(
            category = it.category.orEmpty(),
            count = it.count ?: 0,
            description = it.description.orEmpty(),
            id = it.productId ?: 1,
            imageOne = it.imageOne.orEmpty(),
            imageThree = it.imageTwo.orEmpty(),
            imageTwo = it.imageThree.orEmpty(),
            price = it.price ?: 0.0,
            rate = it.rate ?: 0.0,
            salePrice = it.salePrice ?: 0.0,
            saleState = it.saleState ?: false,
            title = it.title.orEmpty(),
        )
    }

fun List<ProductUI>.mapToProductEntity() =
    map{
        FavProductEntity(
            category = it.category,
            count = it.count,
            description = it.description,
            productId = it.id,
            imageOne = it.imageOne,
            imageThree = it.imageTwo,
            imageTwo = it.imageThree,
            price = it.price,
            rate = it.rate,
            salePrice = it.salePrice,
            saleState = it.saleState,
            title = it.title
        )
    }
