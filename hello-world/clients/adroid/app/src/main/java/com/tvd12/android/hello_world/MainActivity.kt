package com.tvd12.android.hello_world

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tvd12.ezyfoxserver.client.logger.EzyLogger
import com.tvd12.ezyfoxserver.client.socket.EzyMainEventsLoop

class MainActivity : AppCompatActivity() {
    val mainEventsLoop = EzyMainEventsLoop()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.mainEventsLoop.start()
        SocketClientProxy.getInstance().connectToServer();
    }
}