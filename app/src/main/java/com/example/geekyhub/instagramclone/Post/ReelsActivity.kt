package com.example.geekyhub.instagramclone.Post

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.geekyhub.instagramclone.HomeActivity
import com.example.geekyhub.instagramclone.Models.Reel
import com.example.geekyhub.instagramclone.Models.User
import com.example.geekyhub.instagramclone.databinding.ActivityReelsBinding
import com.example.geekyhub.instagramclone.utils.REEL
import com.example.geekyhub.instagramclone.utils.REEL_FOLDER
import com.example.geekyhub.instagramclone.utils.USER_NODE
import com.example.geekyhub.instagramclone.utils.uploadVideo
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ReelsActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityReelsBinding.inflate(layoutInflater)
    }
    private lateinit var videourl:String
   lateinit var progressDialog:ProgressDialog
    val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadVideo(uri, REEL_FOLDER,progressDialog) {
                    url->
                if (url != null) {

                    videourl=url
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        progressDialog=ProgressDialog(this)
        binding.selectReel.setOnClickListener(){
            launcher.launch("video/*")
        }
        binding.cancelBtn.setOnClickListener(){
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }

        binding.postBtn.setOnClickListener(){
            val auth= FirebaseAuth.getInstance().currentUser?.uid
            if (auth != null) {
                Firebase.firestore.collection(USER_NODE).document(auth).get().addOnSuccessListener {
                    var user:User=it.toObject<User>()!!
                    val reel: Reel = Reel(videourl!!,binding.caption.text.toString(),user.image!!)
                    val uid= FirebaseAuth.getInstance().currentUser?.uid
                    Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {
                        if (uid != null) {
                            Firebase.firestore.collection(uid+ REEL).document().set(reel).addOnSuccessListener {
                                startActivity(Intent(this@ReelsActivity,HomeActivity::class.java))
                                finish()
                            }
                        }



                    }
                }
                }


        }


    }
}