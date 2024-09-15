package com.example.postanger.Room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class ItemViewModel(private val itemRepository: ItemRepository) : ViewModel() {

    fun insertItem(item: ItemDatabase) {
        itemRepository.insertItem(item)
    }

    fun getAllItem(): LiveData<List<ItemDatabase>> {
        return itemRepository.getAllItem()
    }

    fun updateItem(item: ItemDatabase) {
        itemRepository.updateItem(item)
    }

    fun deleteItem(item: ItemDatabase) {
        itemRepository.deleteItem(item)
    }
}