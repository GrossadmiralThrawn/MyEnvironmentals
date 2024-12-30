package com.myenvironmentals.models.Connections




interface IConnection {
    fun connect(): Boolean
    fun send(data: String): Boolean
    fun receive(): ByteArray
    fun disconnect()
}