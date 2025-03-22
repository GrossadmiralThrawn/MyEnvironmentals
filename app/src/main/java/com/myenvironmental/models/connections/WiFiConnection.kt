package com.myenvironmental.models.connections






import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import com.myenvironmental.models.connections.errors.ResultText
import com.myenvironmental.models.connections.errors.NetworkError
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import com.myenvironmental.models.connections.errors.Result




class WiFiConnection (private val context: Context,
                      private val engine: HttpClientEngine,
                      private val standardURL: String)
{
    private val connectivityManager     = context.getSystemService<ConnectivityManager>()!!
    private val _connectionStatus = MutableStateFlow(false)
    val connectionStatus: StateFlow<Boolean> = _connectionStatus
    private val httpClient: HttpClient




    init {
        monitorWLANConnectivity()
        httpClient = createHttpClient()
    }




    fun createHttpClient(): HttpClient {
        return HttpClient(engine) {
            install(Logging)
            {
                LogLevel.ALL
            }
            install(ContentNegotiation)
            {
                json(json = Json{
                    ignoreUnknownKeys = true
                }
                )
            }
        }
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




    //            val response = httpClient.get(uRL ?: standardURL) {
    suspend fun sendAndReceive(toSendData: String, uRL: String? = null): Result<String, NetworkError> {
        val response = try {
            httpClient.get(
                urlString = "https://www.purgomalum.com/service/json"
            ) {
                parameter("text", toSendData)
            }
        } catch(e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch(e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }

        return when(response.status.value) {
            in 200..299 -> {
                val censoredText = response.body<ResultText>()
                Result.Success(censoredText.result)
            }
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }
}