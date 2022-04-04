package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    private lateinit var edtemail:EditText
    private lateinit var edtpswrd:EditText
    private lateinit var btnlogin:Button
    private lateinit var btnsignup:Button
    private lateinit var mauth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        edtemail=findViewById(R.id.edt)
        mauth= FirebaseAuth.getInstance()
        edtpswrd=findViewById(R.id.password)
        btnlogin=findViewById(R.id.login)
        btnsignup=findViewById(R.id.signup)
        btnsignup.setOnClickListener{
            val intent= Intent(this,Signup::class.java)
            startActivity(intent)
        }
        btnlogin.setOnClickListener{
            val email=edtemail.text.toString()
            val password=edtpswrd.text.toString()
            login(email,password)
        }
    }
    private fun login(email:String,password:String){
        mauth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // code for logging in
                    val intent=Intent(this,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"User does not exist",Toast.LENGTH_SHORT).show()
                }
            }

    }
}