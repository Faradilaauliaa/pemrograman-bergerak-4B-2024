package com.example.postanger.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItem(itemDatabase: ItemDatabase)

    @Query("SELECT * FROM ItemDatabase ORDER BY id DESC")
    fun getAllItem() : LiveData<List<ItemDatabase>>

    @Update
    fun updateItem(item: ItemDatabase)

    @Delete
    fun deleteItem(item: ItemDatabase)
}