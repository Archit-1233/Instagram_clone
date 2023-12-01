package com.example.geekyhub.instagramclone.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.geekyhub.instagramclone.Models.Post
import com.example.geekyhub.instagramclone.R
import com.example.geekyhub.instagramclone.adapters.MyPostRvAdapter
import com.example.geekyhub.instagramclone.databinding.FragmentMypostFragementBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso


class My_post_Fragement : Fragment(),MyPostRvAdapter.OnItemClickListener {
private lateinit var binding: FragmentMypostFragementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            binding=FragmentMypostFragementBinding.inflate(inflater,container,false)
            var postList= ArrayList<Post>()
            var adapter=MyPostRvAdapter(requireContext(),postList,this)
            binding.rv.layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
            binding.rv.adapter=adapter
            var uid=FirebaseAuth.getInstance().currentUser?.uid
            if (uid != null) {
                Firebase.firestore.collection(uid).get().addOnSuccessListener {
                    var tempList= arrayListOf<Post>()
                    for(i in it.documents){
                        var post: Post? =i.toObject<Post>()
                        if (post != null) {
                            tempList.add(post)
                        }
                    }
                    postList.addAll(tempList)
                    adapter.notifyDataSetChanged()
                }
            }
            return binding.root
        }

        companion object {

        }

    override fun onItemClick(post: Post) {
        val imageUrl = post.postUrl
        openImage(imageUrl)
    }
    private fun openImage(imageUrl: String) {
        // Implement the logic to open the image (e.g., show in a dialog)
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_image)

        val dialogImageView: ImageView = dialog.findViewById(R.id.dialogImageView)

        Picasso.get().load(imageUrl).into(dialogImageView)

        dialog.show()
    }

}