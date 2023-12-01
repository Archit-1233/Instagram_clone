
package com.example.geekyhub.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.geekyhub.instagramclone.Models.Post
import com.example.geekyhub.instagramclone.databinding.MyPostRvDesignBinding
import com.squareup.picasso.Picasso

class MyPostRvAdapter(private val context: Context, private var postList: List<Post>, private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MyPostRvAdapter.ViewHolder>() {

    // Interface for item click events
    interface OnItemClickListener {
        fun onItemClick(post: Post)
    }

    inner class ViewHolder(val binding: MyPostRvDesignBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(postList[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MyPostRvDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Picasso.get().load(postList[position].postUrl).into(holder.binding.postImage)
    }

    // Function to update the post list
    fun updateList(newList: List<Post>) {
        postList = newList
        notifyDataSetChanged()
    }
}


