package com.example.postanger

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.ReturnMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.example.postanger.Room.ItemDatabase
import com.example.postanger.Room.ItemViewModel
import com.example.postanger.Room.ItemViewModelFactory
import com.example.postanger.Utils.reduceFileImage
import com.example.postanger.Utils.uriToFile
import com.google.android.material.textfield.TextInputEditText
import java.io.File

class UpdateItemRoomActivity : AppCompatActivity() {
    private var currentImageUri: Uri? = null
    private var oldPhoto: File? = null
    //
    private lateinit var itemViewModel: ItemViewModel
    private lateinit var vPostDesc: TextInputEditText
    private lateinit var vPostImage: ImageView
    private lateinit var vText_img: TextView
    private lateinit var getData: ItemDatabase

    private val imagePickerLauncher = registerImagePicker {
        val firstImage = it.firstOrNull() ?: return@registerImagePicker
        if (firstImage.uri.toString().isNotEmpty()) {
            vPostImage.visibility = View.VISIBLE
            currentImageUri = firstImage.uri
            vText_img.setText("change")
            Glide.with(vPostImage)
                .load(firstImage.uri)
                .into(vPostImage)
        } else {
            View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_update_item_room)

        getData = intent.getParcelableExtra("post")!!
        val postViewModelFactory = ItemViewModelFactory.getInstance(this)
        itemViewModel = ViewModelProvider(this, postViewModelFactory)[ItemViewModel::class.java]

        vPostImage = findViewById(R.id.post_img_update)
        vPostDesc = findViewById(R.id.post_desc_update)
        vText_img = findViewById(R.id.text_img_update)

        vPostDesc.setText(getData!!.description)
        vText_img.setText("change")

        oldPhoto = getData.image
        Glide.with(vPostImage)
            .load(getData.image)
            .into(vPostImage)

        onClick()
    }

    private fun onClick() {
        val openImagePicker = findViewById<ImageView>(R.id.post_img_update)
        openImagePicker.setOnClickListener {
            imagePickerLauncher.launch(
                ImagePickerConfig {
                    mode = ImagePickerMode.SINGLE
                    returnMode = ReturnMode.ALL
                    isFolderMode = true
                    folderTitle = "Galeri"
                    isShowCamera = false
                    imageTitle = "Click to choice the image"
                    doneButtonText = "Done"
                }
            )
        }

        val btnSavedPlayer = findViewById<Button>(R.id.btn_updatedPost)
        btnSavedPlayer.setOnClickListener {
            if (validateInput()) {
                savedData()
            }
        }
    }

    private fun validateInput(): Boolean {
        var error = 0

        if (vPostDesc.text.toString().isEmpty()) {
            error++
            vPostDesc.error = "Desc is not empty!"
        }
        if (vText_img.text.toString() == "add") {
            error++
            vText_img.error = "Image is not Empty!"
        }

        return error == 0
    }

    private fun savedData() {
        val imageFile = currentImageUri?.let { uriToFile(it, this).reduceFileImage() }

        val item = (if (currentImageUri != null) imageFile else oldPhoto)?.let {
            val descriptionText = vPostDesc.text.toString()
            val words = descriptionText.split(" ")
            val firstTwoWords = words.take(2).joinToString(" ")
            ItemDatabase(
                id = getData.id,
                name = firstTwoWords,
                description = descriptionText,
                image = it,
                like = getData.like
            )
        }

        if (item != null){
            Log.d("UpdatePostActivity", "Post Data: $item")
            itemViewModel.updateItem(item)
            Toast.makeText(
                this@UpdateItemRoomActivity,
                "Data Success Updated",
                Toast.LENGTH_SHORT
            ).show()
        }else{
            Toast.makeText(
                this@UpdateItemRoomActivity,
                "Data Failure Updated",
                Toast.LENGTH_SHORT
            ).show()
        }

        finish()
    }

    fun toMain(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}