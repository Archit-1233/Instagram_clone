

//package com.example.geekyhub.instagramclone.adapters
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.geekyhub.instagramclone.Models.Post
//import com.example.geekyhub.instagramclone.Models.User
//import com.example.geekyhub.instagramclone.R
//import com.example.geekyhub.instagramclone.databinding.PostRvBinding
//import com.example.geekyhub.instagramclone.utils.USER_NODE
//import com.google.firebase.Firebase
//import com.google.firebase.firestore.firestore
//import com.google.firebase.firestore.toObject
//
//class PostAdapter(var context:Context,var postList:ArrayList<Post>):RecyclerView.Adapter<PostAdapter.MyHolder>() {
//    inner class MyHolder(var binding: PostRvBinding):RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
//        var binding=PostRvBinding.inflate(LayoutInflater.from(context),parent,false)
//        return MyHolder(binding)
//
//    }
//
//    override fun getItemCount(): Int {
//        return postList.size
//    }
//
//    override fun onBindViewHolder(holder: MyHolder, position: Int) {
//   try {
//       Firebase.firestore.collection(USER_NODE).document(postList.get(position).uid).get().addOnSuccessListener {
//           var user=it.toObject<User>()
//           Glide.with(context).load(user!!.image).placeholder(R.drawable.user).into(holder.binding.profileImage)
//           holder.binding.name.text=user.name
//           Glide.with(context).load(postList.get(position).postUrl).placeholder(R.drawable.loading).into(holder.binding.postImage)
//           holder.binding.time.text=postList.get(position).time
//           holder.binding.caption.text=postList.get(position).caption
//           holder.binding.like.setOnClickListener(){
//               holder.binding.like.setImageResource(R.drawable.heartlike)
//           }
//       }
//
//   } catch(e:Exception){
//
//   }
//    }
//
//}
package com.example.geekyhub.instagramclone.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.geekyhub.instagramclone.Models.Post
import com.example.geekyhub.instagramclone.Models.User
import com.example.geekyhub.instagramclone.R
import com.example.geekyhub.instagramclone.databinding.PostRvBinding
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class PostAdapter(var context: Context, var postList: ArrayList<Post>) :
    RecyclerView.Adapter<PostAdapter.MyHolder>() {

    inner class MyHolder(var binding: PostRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = PostRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        try {
            val userId = postList[position].uid

            // Fetch user data from Firestore
            FirebaseFirestore.getInstance().collection(USER_NODE).document(userId)
                .get()
                .addOnSuccessListener { documentSnapshot: DocumentSnapshot ->
                    val user = documentSnapshot.toObject<User>()

                    // Check for null user
                    if (user != null) {
                        // Update UI with user details
                        Glide.with(context).load(user.image).placeholder(R.drawable.user)
                            .into(holder.binding.profileImage)
                        holder.binding.name.text = user.name
                    } else {
                        // Handle the case where user data is null
                        // You may want to set default values or show an error message
                    }
                }
                .addOnFailureListener { e: Exception ->
                    // Handle failures, log the exception, or show an error message
                    e.printStackTrace()
                }

            // Load post details
            Glide.with(context).load(postList[position].postUrl)
                .placeholder(R.drawable.loading).into(holder.binding.postImage)
            try {
                val  text = TimeAgo.using(postList.get(position).time.toLong())
                holder.binding.time.text = text
            }catch (e:Exception){
                holder.binding.time.text = " "
            }
holder.binding.share.setOnClickListener(){
    var i=Intent(Intent.ACTION_SEND)
    i.type="text/plain"
    i.putExtra(Intent.EXTRA_TEXT,postList.get(position).postUrl)
    context.startActivity(i)
}

            holder.binding.caption.text = postList[position].caption

            holder.binding.like.setOnClickListener {
                holder.binding.like.setImageResource(R.drawable.heartlike)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        const val USER_NODE = "User"
    }
}

