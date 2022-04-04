package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class message_adapter(val context: Context,val messageList:ArrayList<message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val Item_rcv=1
    val Item_sent=2
    class SentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val sent=itemView.findViewById<TextView>(R.id.txt_sent)
    }
    class RecieveViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val txt=itemView.findViewById<TextView>(R.id.txt_rcv)
    }

    override fun getItemViewType(position: Int): Int {
        val currentmessage=messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentmessage.senderId)){
            return Item_sent
        }
        else{
            return Item_rcv
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentmessage=messageList[position]
        if(holder.javaClass==SentViewHolder::class.java){

            val holder=holder as SentViewHolder
            holder.sent.text=currentmessage.message
        }
        else{
            val holder=holder as RecieveViewHolder
            holder.txt.text=currentmessage.message
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==1){
            val view:View=LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            return RecieveViewHolder(view)
        }
        else{
            val view:View=LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return SentViewHolder(view)
        }
    }
}