package com.example.chatapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class UserAdapter( c:Context,val userList:ArrayList<User> ): RecyclerView.Adapter<UserAdapter.UserViewHolder>(){
    var context:Context=c
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return UserViewHolder(view)
    }
    class UserViewHolder(ItemView:View):RecyclerView.ViewHolder(ItemView){
        val textName=ItemView.findViewById<TextView>(R.id.txt)

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser=userList[position]
        holder.textName.text=currentUser.name
        holder.itemView.setOnClickListener{
            val intent= Intent(context,chat_activity::class.java)
            intent.putExtra("name",currentUser.name)
            intent.putExtra("uid",currentUser.uid)
            context.startActivity(intent)
        }
    }


}


