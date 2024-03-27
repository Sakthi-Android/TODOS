package com.example.todos.database

import androidx.compose.ui.text.input.TextFieldValue
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.todos.model.Products
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(productEntity: ProductEntity)

    @Query("SELECT * FROM ProductEntity")
    fun getAllData(): Flow<List<ProductEntity>>

    @Query("UPDATE ProductEntity SET title=:value WHERE product_id=:id")
    fun updateOneColumnData( value: String, id : Int)

    @Query("DELETE FROM ProductEntity WHERE product_id=:id")
    fun deleteColumnData(id : Int)
}