package com.sevvalozdamar.sportsgear.data.mapper

import com.sevvalozdamar.sportsgear.data.model.Product
import com.sevvalozdamar.sportsgear.data.model.ProductUI

fun Product.mapToProductUI() =
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
        title = this.title.orEmpty()
    )

fun List<Product>.mapToProductUI() =
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
            title = it.title.orEmpty()
        )
    }