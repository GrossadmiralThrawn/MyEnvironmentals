package com.myenvironmentals.models.connections

import kotlinx.coroutines.flow.StateFlow


interface IConnection {
    val connectionStatus: StateFlow<Boolean>
    fun connect(): Boolean
    fun disconnect()
    fun sendData(data: String): Boolean
    fun receiveData(): String?
    fun isConnected(): Boolean
}