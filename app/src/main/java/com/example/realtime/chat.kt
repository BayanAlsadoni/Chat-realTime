package com.example.realtime

import android.content.Context
import android.graphics.Color
import android.provider.CalendarContract.Colors
import android.text.style.BackgroundColorSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView

class MessagesAdapter(val context: Context, val messages: ArrayList<Message>, val currentUserUid : String) :
    RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {
        class MessageViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
            val messageText : TextView = itemView.findViewById(R.id.message_text)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.message_item,parent,false)
        return  MessageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.messageText.text = message.text
        val layoutParams = holder.messageText.layoutParams as LinearLayout.LayoutParams
        layoutParams.gravity = if (message.senderId == currentUserUid){ Gravity.START}
            else {
            holder.messageText.setBackgroundResource(R.drawable.recive_message)
//            holder.messageText.setBackgroundColor(R.drawable.recive_message)

            Gravity.END
//           holder.messageText.setBackgroundColor(R.drawable.message)
//           holder.messageText.background = context.getResources().getColor(android.R.color.white)

        }
        holder.messageText.layoutParams = layoutParams
    }
}

data class Message(val text : String = "", val senderId : String = "", val receiverId : String ="", val timestamp: String="")