package com.example.geekyhub.instagramclone

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        window.statusBarColor=Color.TRANSPARENT
        Handler().postDelayed({
            if (FirebaseAuth.getInstance().currentUser==null) {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }
            else{
                val intent=Intent(this,HomeActivity::class.java)
                startActivity(intent)
            }
            finish()
        },2000)
    }
}