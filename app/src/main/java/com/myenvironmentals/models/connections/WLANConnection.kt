package com.myenvironmentals.models.connections




import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.core.content.getSystemService




class WLANConnection(private val context: Context): IConnection {
    private val connectivityManager = context.getSystemService<ConnectivityManager>()!!
    private val _connectionStatus   = MutableStateFlow(false)
    override val connectionStatus: StateFlow<Boolean> = _connectionStatus




    init {
        monitorWLANConnectivity()
    }




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




    /**
     * Monitors the device's WLAN (Wi-Fi) connectivity status and updates the connection status.
     */
    private fun monitorWLANConnectivity() {
        val callback = object : NetworkCallback() {
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)

                // Check if the active network is a Wi-Fi network
                val isWLAN = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)

                // Check if the Wi-Fi network is validated and connected to the internet
                val connected = isWLAN && networkCapabilities.hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_VALIDATED
                )

                _connectionStatus.value = connected
            }

            override fun onUnavailable() {
                super.onUnavailable()
                _connectionStatus.value = false
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                _connectionStatus.value = false
            }

            override fun onAvailable(network: Network) {
                super.onAvailable(network)

                // When the network becomes available, check if it is Wi-Fi
                val capabilities = connectivityManager.getNetworkCapabilities(network)
                val isWLAN = capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
                _connectionStatus.value = isWLAN
            }
        }

        // Register the network callback to monitor WLAN connectivity
        connectivityManager.registerDefaultNetworkCallback(callback)
    }
}