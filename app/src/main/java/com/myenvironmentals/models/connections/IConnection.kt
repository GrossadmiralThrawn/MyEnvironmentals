package com.myenvironmentals.models.connections




interface IConnection {
    fun connect(): Boolean
    fun send(data: String): Boolean
    fun receive(): ByteArray
    fun disconnect()
}