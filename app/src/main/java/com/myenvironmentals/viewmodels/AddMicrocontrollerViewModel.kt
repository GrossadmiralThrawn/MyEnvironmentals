package com.myenvironmentals.viewmodels




import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myenvironmentals.models.connections.IConnection
import com.myenvironmentals.models.settings.IReadSettings
import com.myenvironmentals.ui.theme.BodyDark
import com.myenvironmentals.ui.theme.TopBarDark
import com.myenvironmentals.ui.theme.White
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch




class AddMicrocontrollerViewModel(private val iReadSettings: IReadSettings) : ViewModel() {
    private val _navigateToConnectionScreen = MutableStateFlow<IConnection?>(null)
    val navigateToConnectionScreen: StateFlow<IConnection?> = _navigateToConnectionScreen
    private val _topBarColor = MutableStateFlow(TopBarDark)
    val topBarColor: StateFlow<Color> = _topBarColor
    private val _bodyColor = MutableStateFlow(BodyDark)
    val bodyColor: StateFlow<Color> = _bodyColor
    private val _fontColor = MutableStateFlow(White)
    val fontColor: StateFlow<Color> = _fontColor
    private lateinit var iConnection: IConnection
    private val _connectionStatus = MutableStateFlow(false)
    val connectionStatus: StateFlow<Boolean> = _connectionStatus




    init {
        _topBarColor.value = this.getColor('t')
        _bodyColor.value = this.getColor('b')
        _fontColor.value = this.getColor('f')
    }

    fun getColor(position: Char): Color {
        return iReadSettings.getColor(position)
    }




    fun navigateToConnectionScreen(iConnection: IConnection) {
        _navigateToConnectionScreen.value = iConnection
    }




    fun setConnectionType(iConnection: IConnection) {
        this.iConnection = iConnection

        // Beginne erst hier mit dem Abonnement von connectionStatus
        viewModelScope.launch {
            iConnection.connectionStatus.collectLatest { status ->
                _connectionStatus.value = status
            }
        }
    }




    fun getConnectionType(): IConnection {
        return iConnection
    }
}
