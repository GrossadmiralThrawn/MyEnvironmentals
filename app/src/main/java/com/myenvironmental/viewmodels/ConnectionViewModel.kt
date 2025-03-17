package com.myenvironmental.viewmodels




import androidx.compose.ui.graphics.Color
import com.myenvironmental.models.connections.WiFiConnection
import com.myenvironmental.models.settings.IReadSettings
import com.myenvironmental.ui.theme.BodyDark
import com.myenvironmental.ui.theme.TopBarDark
import com.myenvironmental.ui.theme.White
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class ConnectionViewModel(private val iSettingsReader: IReadSettings, private val wiFiConnection: WiFiConnection) {
    private val _topBarColor   = MutableStateFlow(TopBarDark)
    val topBarColor : StateFlow<Color>                 = _topBarColor
    private val _bodyColor     = MutableStateFlow(BodyDark)
    val bodyColor : StateFlow<Color>                   = _bodyColor
    private val _fontColor     = MutableStateFlow(White)
    val fontColor : StateFlow<Color>                   = _fontColor
    private val _backendConnected = MutableStateFlow(wiFiConnection.connectionStatus)
    private val _connectedToWiFi = MutableStateFlow(true)
    val connectedToWiFi = _connectedToWiFi
}