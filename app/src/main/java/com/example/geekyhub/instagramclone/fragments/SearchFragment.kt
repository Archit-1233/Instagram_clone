package com.example.geekyhub.instagramclone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.geekyhub.instagramclone.Models.User
import com.example.geekyhub.instagramclone.adapters.SearchAdapter
import com.example.geekyhub.instagramclone.databinding.FragmentSearchBinding
import com.example.geekyhub.instagramclone.utils.USER_NODE
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    lateinit var adapter: SearchAdapter
    var userList=ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding=FragmentSearchBinding.inflate(inflater, container, false)
binding.rv.layoutManager=LinearLayoutManager(requireContext())
        adapter= SearchAdapter(requireContext(),userList)
        binding.rv.adapter=adapter

        Firebase.firestore.collection(USER_NODE).get().addOnSuccessListener {
            var tempList=ArrayList<User>()
            for(i in it.documents) {
                val currentuser = FirebaseAuth.getInstance().currentUser
                if (i.id.toString().equals(currentuser?.uid.toString())) {

                } else {
                    var user: User = i.toObject<User>()!!
                    tempList.add(user)
                }
                userList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }
        }

        binding.searchBtn.setOnClickListener(){
         val text=binding.searchView.text.toString()
            //compoundquery
            Firebase.firestore.collection(USER_NODE).whereEqualTo("name",text).get().addOnSuccessListener {
                var tempList=ArrayList<User>()
                userList.clear()
                if (it.isEmpty){

                }
                else {
                    for (i in it.documents) {
                        val currentuser = FirebaseAuth.getInstance().currentUser
                        if (i.id.toString().equals(currentuser?.uid.toString())) {

                        } else {
                            var user: User = i.toObject<User>()!!
                            tempList.add(user)
                        }

                    }
                    userList.addAll(tempList)
                    adapter.notifyDataSetChanged()
                }
            }
        }
        


        return binding.root
    }

    companion object {

    }
}