package com.example.geekyhub.instagramclone.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.geekyhub.instagramclone.Models.User
import com.example.geekyhub.instagramclone.SignUpActivity
import com.example.geekyhub.instagramclone.adapters.ViewPagerAdapter
import com.example.geekyhub.instagramclone.databinding.FragmentProfileBinding
import com.example.geekyhub.instagramclone.utils.USER_NODE
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {
    private lateinit var binding:FragmentProfileBinding
     private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentProfileBinding.inflate(inflater,container,false)

        binding.editProfileBtn.setOnClickListener(){
            val intent=Intent(activity,SignUpActivity::class.java)
            intent.putExtra("MODE",1)
           activity?.startActivity(intent)
            activity?.finish()
        }
        viewPagerAdapter= ViewPagerAdapter(requireActivity().supportFragmentManager)
        viewPagerAdapter.addFragments(My_post_Fragement(),"My Posts")
        viewPagerAdapter.addFragments(My_reel_fragement(),"My Reels")
        binding.viewPager.adapter=viewPagerAdapter
        binding.tablayout.setupWithViewPager(binding.viewPager)
        return binding.root
    }

    companion object {
    }

    override fun onStart() {
        val auth=FirebaseAuth.getInstance()
        super.onStart()
        Firebase.firestore.collection( USER_NODE).document(auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                var user:User=it.toObject<User>()!!
                binding.name.text=user.name
                binding.bio.text=user.email
 if(!user.image.isNullOrEmpty()){
     Picasso.get().load(user.image).into(binding.profileImage)
 }

            }
    }
}