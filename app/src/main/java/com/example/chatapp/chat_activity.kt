package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class chat_activity : AppCompatActivity() {
    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageBox:EditText
    private lateinit var sendButton:ImageView
    private lateinit var messageAdapter: message_adapter
    private lateinit var messageList:ArrayList<message>
    var recieverRoom:String?=null
    var senderRoom:String?=null
    private lateinit var mdb:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name=intent.getStringExtra("name")
        mdb=FirebaseDatabase.getInstance().getReference()
        val ruid=intent.getStringExtra("uid")
        val suid=FirebaseAuth.getInstance().currentUser?.uid
        senderRoom=ruid+suid
        recieverRoom=suid+ruid
        supportActionBar?.title=name
        messageRecyclerView=findViewById(R.id.chat)
        messageBox=findViewById(R.id.message)
        sendButton=findViewById(R.id.image)
        messageList= ArrayList()
        messageAdapter= message_adapter(this,messageList)
        messageRecyclerView.layoutManager=LinearLayoutManager(this)
        messageRecyclerView.adapter=messageAdapter
        mdb.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for(postSnapshot in snapshot.children){
                    val message=postSnapshot.getValue(message::class.java)
                    messageList.add(message!!)
                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        sendButton.setOnClickListener{
            val message=messageBox.text.toString()
            val messageObject:message= message(message,suid)
            mdb.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mdb.child("chats").child(recieverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            messageBox.setText("")
        }

    }
}