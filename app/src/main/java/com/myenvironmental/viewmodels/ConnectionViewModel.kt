package com.myenvironmental.viewmodels




import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myenvironmental.models.connections.WiFiConnection
import com.myenvironmental.models.settings.IReadSettings
import com.myenvironmental.ui.theme.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch




class ConnectionViewModel(
    private val iSettingsReader: IReadSettings,
    private val wiFiConnection: WiFiConnection,
    private val connectedString: String,
    private val disconnectedString: String
) : ViewModel() {
    private val _topBarColor = MutableStateFlow(TopBarDark)
    val topBarColor: StateFlow<Color> = _topBarColor
    private val _bodyColor = MutableStateFlow(BodyDark)
    val bodyColor: StateFlow<Color> = _bodyColor
    private val _fontColor = MutableStateFlow(White)
    val fontColor: StateFlow<Color> = _fontColor
    private val _connectedToWiFi = MutableStateFlow("Verbunden")
    val connectedToWiFi: StateFlow<String> = _connectedToWiFi
    private val _connectionColor = MutableStateFlow(BrightGreen)
    val connectionColor: StateFlow<Color> = _connectionColor




    init {
        synchronizeColorAndConnectionState()
        _topBarColor.value = this.getColor('t')
        _bodyColor.value = this.getColor('b')
        _fontColor.value = this.getColor('f')
    }




    fun getColor(position: Char): Color {
        return iSettingsReader.getColor(position)
    }




    private fun synchronizeColorAndConnectionState() {
        // Beobachte den Verbindungsstatus und aktualisiere die UI
        viewModelScope.launch {
            wiFiConnection.connectionStatus.collectLatest { isConnected ->
                if (isConnected) {
                    _connectedToWiFi.value = connectedString
                    _connectionColor.value = BrightGreen
                } else {
                    _connectedToWiFi.value = disconnectedString
                    _connectionColor.value = ScarletRed
                }
            }
        }
    }
}
