package com.example.todos.model

import android.os.Parcel
import android.os.Parcelable


data class ProductList(
    var products: List<Products>
)
data class Products(
    var id: Int=0,
    var title: String="",
    var description: String="",
    var price: Int=0,
    var discountPercentage: Float=0f,
    var rating: Float=0f,
    var stock: Int=0,
    var brand: String="",
    var category: String="",
    var thumbnail: String="",
    var images: List<String> = listOf(),
)