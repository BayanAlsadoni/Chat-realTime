package com.example.realtime

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var messagesRecyclerView: RecyclerView
    private lateinit var messageEditText:EditText
    private lateinit var sendButton:FloatingActionButton

    private lateinit var senderUid:String
    private lateinit var receiverUid:String
    private lateinit var messagesRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messagesRecyclerView = findViewById(R.id.messages_recycler)
        messageEditText = findViewById(R.id.messsageEdt)
        sendButton = findViewById(R.id.sendBtn)

        receiverUid = "dV6OIn5pvrh6PnMYmX98NXDiMk92"
        senderUid = "55y4TnBGsuOYfcchbVbytyMQ3xB2"


        sendButton.setOnClickListener{
            val messageText = messageEditText.text.toString().trim()

            if(messageText.isNotEmpty()){
                sendMessage(messageText)
                messageEditText.setText("")
            }

        }

        val messageList = ArrayList<Message>()


        val messageAdapter = MessagesAdapter(this, messageList,senderUid)
        messagesRecyclerView.layoutManager= LinearLayoutManager(this)
        messagesRecyclerView.adapter = messageAdapter
        FirebaseDatabase.getInstance().getReference("chat")
            .addChildEventListener(object : ChildEventListener{
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                    val message = snapshot.getValue(Message::class.java)
                    if (message != null){
                        messageList.add(message)
                        messageAdapter.notifyItemInserted(messageList.size -1)
                        messagesRecyclerView.scrollToPosition(messageList.size -1)

                    }

                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onChildRemoved(snapshot: DataSnapshot) {

                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })



    }

    private fun sendMessage(messageText:String){
        val timestamp = System.currentTimeMillis()

//        val time = String.format("%d min, %d sec",
//            TimeUnit.MILLISECONDS.toMinutes(timestamp),
//            TimeUnit.MILLISECONDS.toSeconds(timestamp) -
//            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timestamp))
//        )
        val seconds = (timestamp / 1000) % 60
        val minutes = (timestamp / (1000 * 60) % 60)
        val hours = (timestamp / (1000 * 60 * 60) % 24)
        val time = "$hours:$minutes:$seconds"

        val message = Message(messageText,senderUid, receiverUid,time)
        FirebaseDatabase.getInstance().getReference("chat").push().setValue(message)

    }

}