package com.khtn.mangashare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.khtn.mangashare.booklist.activity.UserReportActivity
import com.khtn.mangashare.navigation.MainActivity
import kotlinx.android.synthetic.main.activity_add_comic.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val intent = Intent(this, MainActivity::class.java)

        loginBtn.setOnClickListener {
            var isOK=true

            if(emailEdit.text?.isEmpty() == true){
                emailLayout.helperText="Email không được bỏ trống"
                isOK=false
            }else{
                emailLayout.helperText=""
            }

            if(password.text?.isEmpty() == true){
                passwordLayout.helperText="Password không được bỏ trống"
                isOK=false
            }else{
                passwordLayout.helperText=""
            }
            if(isOK){
                Log.d("MyScreen", emailEdit.text.toString())
                if(emailEdit.text?.toString().equals("admin") == true){
                    intent.putExtra("typeUser", "admin")
                }else if(emailEdit.text?.toString().equals("author") == true){
                    intent.putExtra("typeUser", "author")
                }else{
                    intent.putExtra("typeUser", "user")
                }
                startActivity(intent)
                finish()
            }
        }
        goToRegisterPage.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }
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