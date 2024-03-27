package com.example.todos.database

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.example.todos.model.Products
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class ProductRepo(context: Context) {
    private val myExecutor = Executors.newSingleThreadExecutor()
    private val myHandler = Handler(Looper.getMainLooper())
    var db: ProductDAO = ProductDatabase.getDatabase(context).productDAO()

    fun insetData(productEntity: ProductEntity) {
        myExecutor.execute {
            myHandler.post {
                db.insert(productEntity)
            }
        }
    }

    fun updateData(id: Int, textName: Products) {
        myExecutor.execute {
            myHandler.post {
                db.updateOneColumnData(textName.title, id)
            }
        }
    }

    suspend fun getAllData(): Flow<List<ProductEntity>> {
        return withContext(Dispatchers.IO) {
            db.getAllData()
        }
    }

    fun deleteData(id: Int) {
        myExecutor.execute {
            myHandler.post {
                db.deleteColumnData(id)
            }
        }
    }

}