package com.example.postanger.Utils

import android.content.Context
import com.example.postanger.Room.AppDatabase
import com.example.postanger.Room.ItemRepository

object DependencyInjection {
    fun provideRepository(context: Context): ItemRepository {
        val database = AppDatabase.getDatabase(context)
        val appExecutors = AppExecutors()
        val dao = database.itemDao()
        return ItemRepository.getInstance(dao, appExecutors)
    }
}