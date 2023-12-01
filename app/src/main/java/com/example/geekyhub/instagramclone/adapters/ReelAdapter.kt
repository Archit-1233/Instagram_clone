
package com.example.geekyhub.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.geekyhub.instagramclone.Models.Reel
import com.example.geekyhub.instagramclone.databinding.ReelDgBinding
import com.squareup.picasso.Picasso

class ReelAdapter(private val context: Context,  val reelList: ArrayList<Reel>) :
    RecyclerView.Adapter<ReelAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ReelDgBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ReelDgBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentReel = reelList.getOrNull(position)

        currentReel?.let {
            Picasso.get().load(it.profileLink).into(holder.binding.profileImage)
            holder.binding.caption.text = it.caption
            holder.binding.videoView.setVideoPath(it.reelUrl)

            holder.binding.videoView.setOnPreparedListener {
                holder.binding.progressBar.visibility= View.GONE
                holder.binding.videoView.visibility = android.view.View.VISIBLE
                holder.binding.videoView.start()
            }
        }
    }
}

