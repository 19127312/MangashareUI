package com.khtn.mangashare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khtn.mangashare.booklist.activity.UserReportActivity
import com.khtn.mangashare.navigation.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("typeUser", "user")
        startActivity(intent)

//        admin.setOnClickListener {
//            intent.putExtra("typeUser", "admin")
//            startActivity(intent)
//        }
//
//        user.setOnClickListener {
//            intent.putExtra("typeUser", "user")
//            startActivity(intent)
//        }
//
//        author.setOnClickListener {
//            intent.putExtra("typeUser", "author")
//            startActivity(intent)
//        }
    }
}