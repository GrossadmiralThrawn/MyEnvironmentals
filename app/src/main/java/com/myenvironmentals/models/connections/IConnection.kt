package com.myenvironmentals.models.connections




import kotlinx.coroutines.flow.StateFlow




interface IConnection {
    fun connect(): Boolean
    fun send(data: String): Boolean
    fun receive(): ByteArray
    fun disconnect()
    val connectionStatus: StateFlow<Boolean>
}