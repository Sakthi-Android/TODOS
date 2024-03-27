package com.example.todos.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [ProductEntity::class],
    version = 3
)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDAO() : ProductDAO
    companion object {
        @Volatile
        private var INSTANCE: ProductDatabase? = null

        fun     getDatabase(
            context: Context
        ): ProductDatabase {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "ProductEntity",
                ).allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}