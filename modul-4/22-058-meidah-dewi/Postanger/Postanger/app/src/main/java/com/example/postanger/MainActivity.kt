package com.example.postanger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.postanger.Adapter.ItemAdapterRoom
import com.example.postanger.Room.ItemDatabase
import com.example.postanger.Room.ItemViewModel
import com.example.postanger.Room.ItemViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var itemViewModel: ItemViewModel
    private lateinit var itemAdapterRoom: ItemAdapterRoom
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val factory = ItemViewModelFactory.getInstance(this)
        itemViewModel = ViewModelProvider(this, factory)[ItemViewModel::class.java]
        recyclerView = findViewById(R.id.rv_post)
        recyclerView.layoutManager = LinearLayoutManager(this)

        itemViewModel.getAllItem().observe(this) { postData ->
            if (postData != null) {
                itemAdapterRoom = ItemAdapterRoom(postData, itemViewModel) //ini
                recyclerView.adapter = itemAdapterRoom

                itemAdapterRoom.setOnItemClickCallback(object :
                    ItemAdapterRoom.OnItemClickCallback {
                    override fun onMoreClicked(data: ItemDatabase, position: Int) {
                        PopUp(data, position).show(supportFragmentManager, PopUp.TAG)
                    }
                })
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        itemViewModel.getAllItem()
    }

    fun toAddPost(view: View) {
        val intent = Intent(this, addRoom::class.java)
        startActivity(intent)
    }
}