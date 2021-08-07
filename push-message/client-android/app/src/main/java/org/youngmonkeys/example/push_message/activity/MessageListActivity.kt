package org.youngmonkeys.example.push_message.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import org.youngmonkeys.example.push_message.R
import org.youngmonkeys.example.push_message.data.MessageListAdapter
import org.youngmonkeys.example.push_message.socket.SocketClientProxy

class MessageListActivity: AppCompatActivity() {

    private lateinit var messageList: ListView
    private lateinit var messageListAdapter: MessageListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.message_list)
        val loading = findViewById<ProgressBar>(R.id.loading)
        messageListAdapter = MessageListAdapter(this)
        messageList = findViewById(R.id.messageList)
        messageList.adapter = messageListAdapter
        val socketProxy = SocketClientProxy.getInstance();
        socketProxy.onDisconnectedCallback {
        }
        socketProxy.onMessage {
            messageListAdapter.add(it)
        }
        socketProxy.onDisconnectedCallback {
            loading.visibility = View.VISIBLE
        }
        socketProxy.onConnectionFailedCallback {
            val loginIntent = Intent(this, LoginActivity::class.java)
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(loginIntent)
        }
    }
}
