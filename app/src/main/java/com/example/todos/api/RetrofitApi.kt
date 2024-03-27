package com.example.todos.api

import com.example.todos.model.ProductList
import com.example.todos.model.Products
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface RetrofitApi {
    @POST("products/add")
    suspend fun postData(@Body dataModel: Products?): Products

    @GET("products")
    fun getAllData():  Call<ProductList>


    @PATCH("products/{id}")
    suspend fun updateData(@Path("id") id: Int, @Body body: Products): Call<Products>

    @DELETE("products/{id}")
    suspend fun deleteData(@Path("id") id: Int): Call<Products>
}