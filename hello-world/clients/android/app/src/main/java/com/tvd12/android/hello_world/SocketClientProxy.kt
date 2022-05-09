package com.tvd12.android.hello_world

import com.tvd12.ezyfoxserver.client.EzyClient
import com.tvd12.ezyfoxserver.client.EzyClients
import com.tvd12.ezyfoxserver.client.config.EzyClientConfig
import com.tvd12.ezyfoxserver.client.constant.EzyCommand
import com.tvd12.ezyfoxserver.client.entity.EzyApp
import com.tvd12.ezyfoxserver.client.entity.EzyArray
import com.tvd12.ezyfoxserver.client.entity.EzyData
import com.tvd12.ezyfoxserver.client.handler.*
import com.tvd12.ezyfoxserver.client.request.EzyAppAccessRequest
import com.tvd12.ezyfoxserver.client.request.EzyLoginRequest
import com.tvd12.ezyfoxserver.client.request.EzyRequest
import com.tvd12.ezyfoxserver.client.util.EzyEntityObjects

/**
 * Created by tavandung12 on 10/7/18.
 */

const val ZONE_NAME = "example"
const val APP_NAME = "hello-world"

class SocketClientProxy private constructor() {

    private val client = newClient()

    companion object {
        private val INSTANCE = SocketClientProxy()
        fun getInstance() : SocketClientProxy = INSTANCE
    }

    inner class ExHandshakeHandler : EzyHandshakeHandler() {

        override fun getLoginRequest(): EzyRequest {
            return EzyLoginRequest(ZONE_NAME, "youngmonkeys", "123456")
        }
    }

    inner class ExLoginSuccessHandler : EzyLoginSuccessHandler() {
        override fun handleLoginSuccess(responseData: EzyData?) {
            val request = EzyAppAccessRequest(APP_NAME)
            client.send(request)
        }
    }

    inner class ExAccessAppHandler : EzyAppAccessHandler() {
        override fun postHandle(app: EzyApp, data: EzyArray) {
            app.send("secureChat",
                EzyEntityObjects.newObject("who", "Dzung"),
                true
            );
        }
    }

    private fun newClient() : EzyClient {
        val config = EzyClientConfig.builder()
            .zoneName(ZONE_NAME)
            .enableSSL()
//            .enableDebug()
            .build()
        val clients = EzyClients.getInstance()
        val client = clients.newClient(config)
        val setup = client.setup()
        setup.addDataHandler(EzyCommand.HANDSHAKE, ExHandshakeHandler())
        setup.addDataHandler(EzyCommand.LOGIN, ExLoginSuccessHandler())
        setup.addDataHandler(EzyCommand.APP_ACCESS, ExAccessAppHandler())
        return client
    }

    fun connectToServer() {
        val host = "tvd12.com"
        client.connect(host, 3005)
    }
}

