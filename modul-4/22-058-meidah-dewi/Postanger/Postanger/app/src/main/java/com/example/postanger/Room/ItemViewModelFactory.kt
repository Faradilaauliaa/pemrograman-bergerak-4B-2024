package com.example.postanger.Room

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.postanger.Utils.DependencyInjection


class ItemViewModelFactory private constructor(private val itemRepository: ItemRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
            return ItemViewModel(itemRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ItemViewModelFactory? = null
        fun getInstance(context: Context): ItemViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ItemViewModelFactory(DependencyInjection.provideRepository(context))
            }.also { instance = it }
    }
}