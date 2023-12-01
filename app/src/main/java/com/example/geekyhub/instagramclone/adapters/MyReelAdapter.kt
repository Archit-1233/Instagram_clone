package com.example.geekyhub.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.geekyhub.instagramclone.Models.Reel
import com.example.geekyhub.instagramclone.databinding.MyPostRvDesignBinding
import com.example.geekyhub.instagramclone.fragments.My_reel_fragement

class MyReelAdapter(private val context: Context, private var reelList: List<Reel>, private val itemClickListener: My_reel_fragement
) : RecyclerView.Adapter<MyReelAdapter.ViewHolder>() {

    // Interface for item click events
    interface OnItemClickListener {
        fun onItemClick(post: Reel)
    }

    inner class ViewHolder(val binding: MyPostRvDesignBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(reelList[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MyPostRvDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(reelList.get(position).reelUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.binding.postImage)
    }

    // Function to update the post list
    fun updateList(newList: List<Reel>) {
        reelList = newList
        notifyDataSetChanged()
    }
}


