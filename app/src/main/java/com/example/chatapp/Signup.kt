package com.example.chatapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Signup : AppCompatActivity() {
    private lateinit var edtemail: EditText
    private lateinit var edtpswrd: EditText

    private lateinit var btnsignup: Button

    private lateinit var mauth: FirebaseAuth
    private lateinit var mdb:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)




        mauth= FirebaseAuth.getInstance()
        edtemail=findViewById(R.id.edt)
        var name:EditText?=findViewById(R.id.Name)
        mauth= FirebaseAuth.getInstance()
        edtpswrd=findViewById(R.id.password)


        btnsignup=findViewById(R.id.signup)

        btnsignup.setOnClickListener{
            val email=edtemail.text.toString()
            val name=name!!.text.toString()
            val password=edtpswrd.text.toString()
            signup(name,email,password)
        }
    }
    private fun signup(name:String,email:String,password:String){
        mauth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //code for jumping to home
                    addUsertoDatabse(name,email,mauth.currentUser?.uid!!)
                    val intent=Intent(this,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this,"Some error Occurred",Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun addUsertoDatabse(name:String,email:String,uid:String){
        mdb=FirebaseDatabase.getInstance().getReference()
        mdb.child("user").child(uid).setValue(User(name,email,uid))
    }
}
