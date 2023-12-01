package com.example.geekyhub.instagramclone.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.geekyhub.instagramclone.Models.Reel
import com.example.geekyhub.instagramclone.R
import com.example.geekyhub.instagramclone.adapters.MyReelAdapter
import com.example.geekyhub.instagramclone.databinding.FragmentMyReelFragementBinding
import com.example.geekyhub.instagramclone.utils.REEL
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso


class My_reel_fragement : Fragment() {
    private lateinit var binding: FragmentMyReelFragementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentMyReelFragementBinding.inflate(inflater, container, false)

        var reelList= ArrayList<Reel>()
        var adapter= MyReelAdapter(requireContext(),reelList,this)
        binding.rv.layoutManager= StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter=adapter
        var uid= FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            Firebase.firestore.collection(uid+ REEL).get().addOnSuccessListener {
                var tempList= arrayListOf<Reel>()
                for(i in it.documents){
                    var reel: Reel? =i.toObject<Reel>()
                    if (reel != null) {
                        tempList.add(reel)
                    }
                }
                reelList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }
        }
        return binding.root
    }

    companion object {

    }
    fun onItemClick(reel: Reel) {
        val imageUrl = reel.reelUrl
        openImage(imageUrl)
    }
    private fun openImage(reelUrl: String) {
        // Implement the logic to open the image (e.g., show in a dialog)
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_image)

        val dialogImageView: ImageView = dialog.findViewById(R.id.dialogImageView)

        Picasso.get().load(reelUrl).into(dialogImageView)

        dialog.show()
    }


}