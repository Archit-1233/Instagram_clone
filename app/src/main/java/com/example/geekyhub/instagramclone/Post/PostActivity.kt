package com.example.geekyhub.instagramclone.Post
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.geekyhub.instagramclone.HomeActivity
import com.example.geekyhub.instagramclone.Models.Post
import com.example.geekyhub.instagramclone.Models.User
import com.example.geekyhub.instagramclone.databinding.ActivityPostBinding
import com.example.geekyhub.instagramclone.utils.POST
import com.example.geekyhub.instagramclone.utils.POST_FOLDER
import com.example.geekyhub.instagramclone.utils.USER_NODE
import com.example.geekyhub.instagramclone.utils.uploadImage
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class PostActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }
    var imageurl: String? =null
    val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(uri, POST_FOLDER) {
                url->
                if (url != null) {
             binding.selectImage.setImageURI(uri)
                    imageurl=url
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Set up the MaterialToolbar
        setSupportActionBar(binding.materialToolbar)

        // Enable the back arrow in the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Set the navigation click listener to finish the activity
        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@PostActivity,HomeActivity::class.java))
            finish()
        }
        binding.selectImage.setOnClickListener(){
            launcher.launch("image/*")
        }
        binding.cancelBtn.setOnClickListener(){
            startActivity(Intent(this@PostActivity,HomeActivity::class.java))
            finish()
        }

binding.postBtn.setOnClickListener() {

    val uid = FirebaseAuth.getInstance().currentUser?.uid
    Firebase.firestore.collection(USER_NODE).document(uid!!).get().addOnSuccessListener {
        var user = it.toObject<User>()!!


       val post: Post = Post(postUrl = imageurl!!,
          caption= binding.caption.text.toString(),
          uid =uid!!,
         time= System.currentTimeMillis().toString())



        Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
            if (uid != null) {
                Firebase.firestore.collection(uid).document().set(post).addOnSuccessListener {
                    startActivity(Intent(this@PostActivity, HomeActivity::class.java))
                    finish()
                }
            }

        }
    }
}


    }

    }





