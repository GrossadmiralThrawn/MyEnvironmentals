package com.myenvironmentals.viewmodels

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.myenvironmentals.models.connections.IConnection
import com.myenvironmentals.models.settings.IReadSettings
import com.myenvironmentals.models.settings.StandardSettingsReader
import com.myenvironmentals.ui.theme.BodyDark
import com.myenvironmentals.ui.theme.TopBarDark
import com.myenvironmentals.ui.theme.White
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class ConnectionViewModel (private val context: Context){
    private val iReadSettings: StandardSettingsReader = StandardSettingsReader(context)
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
}