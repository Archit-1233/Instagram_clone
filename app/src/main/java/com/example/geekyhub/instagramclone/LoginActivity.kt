package com.example.geekyhub.instagramclone

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.geekyhub.instagramclone.Models.User
import com.example.geekyhub.instagramclone.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.loginBtn.setOnClickListener(){
            if(binding.email.text.toString().equals("")or
                    binding.password.text.toString().equals("")){
                Toast.makeText(this,"Please fil all the details",Toast.LENGTH_SHORT).show()
            }else{
                var user=User(binding.email.text.toString(),
                    binding.password.text.toString())
                val auth = FirebaseAuth.getInstance()
               auth.signInWithEmailAndPassword(user.email.toString(), user.password.toString()).addOnCompleteListener {
                   if (it.isSuccessful){
                       val intent=Intent(this,HomeActivity::class.java)
                       startActivity(intent)
                       finish()
                   }
                   else{
                       Toast.makeText(this,it.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                   }
               }
            }
        }
        binding.createNewAccountBtn.setOnClickListener(){
            val intent=Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

    }
}