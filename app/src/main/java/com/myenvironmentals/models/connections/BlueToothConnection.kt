package com.myenvironmentals.models.connections




import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.getSystemService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow




class BlueToothConnection(private val context: Context, ): IConnection {
    private val connectivityManager = context.getSystemService<ConnectivityManager>()!!
    private val _connectionStatus   = MutableStateFlow(false)
    override val connectionStatus: StateFlow<Boolean> = _connectionStatus



    override fun connect(): Boolean {
        TODO("Not yet implemented")
    }

    override fun disconnect() {
        TODO("Not yet implemented")
    }

    override fun sendData(data: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun receiveData(): String? {
        TODO("Not yet implemented")
    }

    override fun isConnected(): Boolean {
        TODO("Not yet implemented")
    }
}