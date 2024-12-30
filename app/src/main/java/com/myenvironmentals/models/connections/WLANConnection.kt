package com.myenvironmentals.models.connections




class WLANConnection: IConnection {
    override fun connect(): Boolean {
        TODO("Not yet implemented")
    }

    override fun send(data: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun receive(): ByteArray {
        TODO("Not yet implemented")
    }

    override fun disconnect() {
        TODO("Not yet implemented")
    }
}