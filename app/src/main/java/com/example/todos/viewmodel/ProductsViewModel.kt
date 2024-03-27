package com.example.todos.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todos.api.RetrofitInstance
import com.example.todos.database.ProductEntity
import com.example.todos.database.ProductRepo
import com.example.todos.model.ProductList
import com.example.todos.model.Products
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ProductsViewModel() : ViewModel() {
    private val apiService = RetrofitInstance.api
    val productsList: MutableState<List<Products>> = mutableStateOf(emptyList())
    private val _addedData = MutableLiveData<Products>()
    private val _dataListFromDB = MutableStateFlow(emptyList<ProductEntity>())
    val dataListFromDB = _dataListFromDB.asStateFlow()
    var selectedData = mutableStateOf(Products())
        private set

    fun data(data: Products) {
        selectedData.value = data
    }

    fun getProducts(repo: ProductRepo) {
        viewModelScope.launch {
            try {
                val response = apiService.getAllData()
                response.enqueue(object : Callback<ProductList> {
                    override fun onResponse(
                        call: Call<ProductList>,
                        response: Response<ProductList>
                    ) {
                        if (response.isSuccessful) {
                            val post = response.body()
                            if (post != null) {
                                productsList.value = post.products
                                viewModelScope.launch(Dispatchers.Main) {
                                    post.products.forEach {
                                        repo.insetData(
                                            ProductEntity(
                                                product_id = it.id,
                                                title = it.title,
                                                description = it.description,
                                                price = it.price,
                                                discountPercentage = it.discountPercentage,
                                                rating = it.rating,
                                                stock = it.stock,
                                                brand = it.brand,
                                                category = it.category,
                                                thumbnail = it.thumbnail
                                            )
                                        )

                                    }
                                }
                            }
                        } else {
                            val errorBody = response.errorBody()
                            Log.e("Error", errorBody.toString())
                        }
                    }
                    override fun onFailure(call: Call<ProductList>, t: Throwable) {
                        Log.e("Error", t.localizedMessage)

                    }
                })
            } catch (e: IOException) {
                Log.e("Error", e.localizedMessage)
            }
        }
    }

    fun getAllData(repo: ProductRepo) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                repo.getAllData().collectLatest {
                    _dataListFromDB.tryEmit(it)
                }
            }
            catch (e: IOException) {
                Log.e("Error", e.localizedMessage)
            }
        }
    }

    fun addData(data: Products, repo: ProductRepo) {
        viewModelScope.launch {
            try {
                val response = apiService.postData(data)
                _addedData.value = response
                repo.insetData(
                    ProductEntity(
                        product_id = response.id,
                        title = response.title,
                        description = response.description,
                        price = response.price,
                        discountPercentage = response.discountPercentage,
                        rating = response.rating,
                        stock = response.stock,
                        brand = response.brand,
                        category = response.category,
                        thumbnail = response.thumbnail
                    )
                )
                Log.e("Log", response.toString())
            } catch (e: Exception) {
                Log.e("Error", e.localizedMessage)
            }
        }
    }

    fun updateData(id: Int, textName: Products, repo: ProductRepo) {
        viewModelScope.launch {
//            val response = apiService.updateData(id, textName)
            repo.updateData(id, textName)
//            response.enqueue(object : Callback<Products> {
//                override fun onResponse(call: Call<Products>, response: Response<Products>) {
//                    if (response.isSuccessful) {
//                        val post = response.body()
//                        if (post != null) {
//                            titleData.value = post
//                        }
//
//                    } else {
//                        Log.e("Error", "Error")
//                    }
//                }
//
//                override fun onFailure(call: Call<Products>, t: Throwable) {
//                    // Handle failure
//                    Log.e("Error", t.message.toString())
//
//                }
//            })
        }
    }

    fun deleteData(repo: ProductRepo, id: Int) {
        viewModelScope.launch {
            try {
//                apiService.deleteData(id)
                repo.deleteData(id)
            }
            catch (e: IOException) {
                Log.e("Error", e.localizedMessage)
            }
        }
    }
}