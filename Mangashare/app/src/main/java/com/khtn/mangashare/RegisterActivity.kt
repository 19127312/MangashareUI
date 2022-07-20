package com.khtn.mangashare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.khtn.mangashare.navigation.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.password

class RegisterActivity : AppCompatActivity() {
    var type=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val intent = Intent(this, MainActivity::class.java)

        backToLoginPage.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        registerBtn.setOnClickListener {
            var isOK=true
            if(hotenEdit.text?.isEmpty() == true){
                hotenLayout.helperText="Họ tên không được bỏ trống"
                isOK=false
            }else{
                hotenLayout.helperText=""
            }

            if(emailRegisterEdit.text?.isEmpty() == true){
                emailRegisterLayout.helperText="Email không được bỏ trống"
                isOK=false
            }else{
                emailRegisterLayout.helperText=""
            }
            if(password.text?.isEmpty() == true){
                passwordRegisterLayout.helperText="Password không được bỏ trống"
                isOK=false
            }else{
                passwordRegisterLayout.helperText=""
            }

            if(isOK){
                if(type==1){
                    intent.putExtra("typeUser", "user")
                }else{
                    intent.putExtra("typeUser", "author")
                }
                startActivity(intent)
                finish()
            }
        }

        authorBtn.setOnClickListener {
            authorClick()
        }

        authorImage.setOnClickListener {
            authorClick()
        }

        userBtn.setOnClickListener {
            userClick()
        }
        userImage.setOnClickListener {
            userClick()
        }
    }

    fun authorClick(){
        type=2
        authorBtn.background= ContextCompat.getDrawable(this, R.drawable.rounded_selected_btn)
        userBtn.background= ContextCompat.getDrawable(this, R.drawable.rounded_btn_unselected)

        authorImage.setImageResource(R.drawable.ic_author_selected)
        userImage.setImageResource(R.drawable.ic_book_open)
    }

    fun userClick(){
        type=1
        authorBtn.background= ContextCompat.getDrawable(this, R.drawable.rounded_btn_unselected)
        userBtn.background= ContextCompat.getDrawable(this, R.drawable.rounded_selected_btn)

        authorImage.setImageResource(R.drawable.ic_author)
        userImage.setImageResource(R.drawable.ic_selected_user)
    }
}