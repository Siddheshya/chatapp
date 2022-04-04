package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var userview:RecyclerView
    private lateinit var userList:ArrayList<User>
    private lateinit var adapter:UserAdapter
    private lateinit var mauth:FirebaseAuth
    private lateinit var mdb:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userList=ArrayList()
        adapter= UserAdapter(this,userList)
        mauth= FirebaseAuth.getInstance()
        userview=findViewById(R.id.user_recycler_view)
        userview.layoutManager=LinearLayoutManager(this)
        userview.adapter=adapter
        mdb=FirebaseDatabase.getInstance().getReference()
        mdb.child("user").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for(postSnapshot in snapshot.children){
                    val currentuser=postSnapshot.getValue(User::class.java)

                    if(mauth.currentUser?.uid!=currentuser?.uid){
                        userList.add(currentuser!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.logout){
            mauth.signOut()
            val intent= Intent(this,Login::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }
}