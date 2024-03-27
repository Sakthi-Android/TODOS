package com.example.todos.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

@Entity(tableName = "ProductEntity",indices = [Index("title", unique = true)])
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @SerialName("id")
    var id: Int = 0,

    @SerialName("product_id")
    var product_id: Int = 0,

    @SerialName("title")
    val title: String = "",

    @SerialName("description")
    val description:String= "",

    @SerialName("price")
    val price: Int = 0,

    @SerialName("discountPercentage")
    val discountPercentage: Float = 0f,
    @SerialName("rating")
    val rating: Float = 0f,
    @SerialName("stock")
    val stock: Int = 0,
    @SerialName("brand")
    val brand: String = "",
    @SerialName("category")
    val category: String = "",
    @SerialName("thumbnail")
    val thumbnail: String = "",
//    @SerializedName("images")
//    @Embedded
//    val images: Image ,
)
data class Image(
    val image: List<String>?=null
)
