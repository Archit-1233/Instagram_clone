
package com.example.geekyhub.instagramclone

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.geekyhub.instagramclone.Models.User
import com.example.geekyhub.instagramclone.databinding.ActivitySignUpBinding
import com.example.geekyhub.instagramclone.utils.USER_NODE
import com.example.geekyhub.instagramclone.utils.USER_PROFILE_FOLDER
import com.example.geekyhub.instagramclone.utils.uploadImage
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso

class SignUpActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        window.statusBarColor = Color.TRANSPARENT
        var user = User() // Create a user object
        val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                uploadImage(uri, USER_PROFILE_FOLDER) {
                    if (it == null) {

                    } else {
                        user.image = it
                        binding.profileImage.setImageURI(uri)
                    }
                }


            }
        }
        if (intent.hasExtra("MODE")) {
            if (intent.getIntExtra("MODE", -1) == 1) {
                val auth = FirebaseAuth.getInstance()
                binding.signUpBtn.text = "Update Profile"
                Firebase.firestore.collection(USER_NODE).document(auth.currentUser!!.uid).get()
                    .addOnSuccessListener {
                        user = it.toObject<User>()!!
                        if(!user.image.isNullOrEmpty()) {
                            Picasso.get().load(user.image).into(binding.profileImage)
                        }
                        binding.name.setText(user.name)
                        binding.email.setText(user.email)
                        binding.password.setText(user.password)

                    }
            }
        }


        binding.signUpBtn.setOnClickListener {
            if (intent.hasExtra("MODE")) {
                if (intent.getIntExtra("MODE", -1) == 1) {
                    val uid = FirebaseAuth.getInstance().currentUser?.uid
                    if (uid != null) {
                        FirebaseFirestore.getInstance().collection(USER_NODE)
                            .document(uid).set(user).addOnSuccessListener {
//                                        Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                            }

                    }
                }
            } else {
                val name = binding.name.text.toString()
                val email = binding.email.text.toString()
                val password = binding.password.text.toString()

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "Please enter all the information", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    // Create a Firebase authentication user
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val uid = FirebaseAuth.getInstance().currentUser?.uid
                                if (uid != null) {
                                    user.name = name
                                    user.email = email
                                    user.password = password

                                    // Store the user data in Firestore
                                    FirebaseFirestore.getInstance().collection(USER_NODE).document(uid).set(user).addOnSuccessListener {
//                                        Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show()
                                            val intent = Intent(this, HomeActivity::class.java)
                                            startActivity(intent)
                                        }.addOnFailureListener { e ->
                                            Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
                                        }
                                }
                            } else {
                                Toast.makeText(this, task.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }
            binding.addImage.setOnClickListener() {
                launcher.launch("image/*")
            }
            binding.login.setOnClickListener() {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

    }
}
