package com.example.geekyhub.instagramclone.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.geekyhub.instagramclone.Models.Post
import com.example.geekyhub.instagramclone.Models.User
import com.example.geekyhub.instagramclone.R
import com.example.geekyhub.instagramclone.adapters.FollowRvAdapter
import com.example.geekyhub.instagramclone.adapters.PostAdapter
import com.example.geekyhub.instagramclone.databinding.FragmentHomeBinding
import com.example.geekyhub.instagramclone.utils.FOLLOW
import com.example.geekyhub.instagramclone.utils.POST
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class HomeFragment : Fragment() {
  private lateinit var binding: FragmentHomeBinding
  private var  postList=ArrayList<Post>()
    private lateinit var adapter:PostAdapter
    private var followlist=ArrayList<User>()
    private lateinit var followRvAdapter:FollowRvAdapter
  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
  }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater, container, false)
        adapter= PostAdapter(requireContext(),postList)
        binding.postRv.layoutManager=LinearLayoutManager(requireContext())
        binding.postRv.adapter=adapter

        binding.followRv.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        followRvAdapter=FollowRvAdapter(requireContext(),followlist)
        binding.followRv.adapter=followRvAdapter

        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.materialToolbar2)
        val currentuser=FirebaseAuth.getInstance().currentUser!!
        Firebase.firestore.collection(currentuser!!.uid + FOLLOW).get().addOnSuccessListener {
        var templist=ArrayList<User>()
        followlist.clear()
            for(i in it.documents){
                var user: User =i.toObject<User>()!!
                templist.add(user)
            }
            followlist.addAll(templist)
            followRvAdapter.notifyDataSetChanged()
        }
        Firebase.firestore.collection(POST).get().addOnSuccessListener {
        var tempList=ArrayList<Post> ()
        postList.clear()
            for(i in it.documents){
                var post:Post=i.toObject<Post>()!!
                tempList.add(post)
            }
            postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }
    companion object {
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
