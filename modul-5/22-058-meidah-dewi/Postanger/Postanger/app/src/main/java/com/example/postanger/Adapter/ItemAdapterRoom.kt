package com.example.postanger.Adapter

import android.content.ClipData.Item
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.postanger.R
import com.example.postanger.Room.ItemDatabase
import com.example.postanger.Room.ItemViewModel
import com.google.android.material.imageview.ShapeableImageView

class ItemAdapterRoom(private var itemList: List<ItemDatabase>, private val itemViewModel: ItemViewModel) :
    RecyclerView.Adapter<ItemAdapterRoom.ItemViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private var stateFav = false

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onMoreClicked(data: ItemDatabase, position: Int)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val postTitle: TextView = itemView.findViewById(R.id.people_name)
        val postDesc: TextView = itemView.findViewById(R.id.item_description)
        val postImg: ShapeableImageView = itemView.findViewById(R.id.item_image)
        val postLike: TextView = itemView.findViewById(R.id.like)
        val postDate: TextView = itemView.findViewById(R.id.time)
        val iconLike: ImageView = itemView.findViewById(R.id.icon_like)

        //btn
        val btnLike: LinearLayout = itemView.findViewById(R.id.btn_like)
        val btnMore: ImageView = itemView.findViewById(R.id.btn_more)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_content, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = itemList[position]

//        holder.postTitle.text = data.name
        holder.postDesc.text = data.description.shorten(500)
        holder.postLike.text = data.like.toString()

        val uri = Uri.fromFile(data.image)
        holder.postImg.setImageURI(uri)
//like nambah
        holder.btnLike.setOnClickListener {
            stateFav = !stateFav
            holder.iconLike.setImageResource(if (stateFav) R.drawable.likeactive else R.drawable.loveicon)
            if (stateFav){
                data.like += 1
            }else{
                data.like -= 1
            }
            holder.postLike.text = data.like.toString()
        }
        // Mengatur aksi ketika button more diklik
        holder.btnMore.setOnClickListener {
            onItemClickCallback.onMoreClicked(itemList[holder.absoluteAdapterPosition], holder.absoluteAdapterPosition)
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun updateItems(items: List<ItemDatabase>) {
        itemList = items
        notifyDataSetChanged()
    }
    private fun String.shorten(maxLength: Int): String {
        return if (this.length <= maxLength) this else "${this.substring(0, maxLength)}..."
    }
}
