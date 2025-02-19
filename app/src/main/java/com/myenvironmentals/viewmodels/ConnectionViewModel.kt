package com.myenvironmentals.viewmodels

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.myenvironmentals.models.connections.IConnection
import com.myenvironmentals.models.settings.StandardSettingsReader
import com.myenvironmentals.screens.ExternalSourceScreen
import com.myenvironmentals.screens.LocalSourceScreen
import com.myenvironmentals.ui.theme.BodyDark
import com.myenvironmentals.ui.theme.TopBarDark
import com.myenvironmentals.ui.theme.White
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow




class ConnectionViewModel (private val context: Context){
    private val iReadSettings: StandardSettingsReader = StandardSettingsReader(context)
    private val _topBarColor = MutableStateFlow(TopBarDark)
    val topBarColor: StateFlow<Color> = _topBarColor
    private val _bodyColor = MutableStateFlow(BodyDark)
    val bodyColor: StateFlow<Color> = _bodyColor
    private val _fontColor = MutableStateFlow(White)
    val fontColor: StateFlow<Color> = _fontColor
    private lateinit var iConnection: IConnection
    private val _selectedScreen = MutableStateFlow<@Composable (() -> Unit)>({})
    val selectedScreen: StateFlow<@Composable (() -> Unit)> = _selectedScreen





    init {
        _topBarColor.value = this.getColor('t')
        _bodyColor.value = this.getColor('b')
        _fontColor.value = this.getColor('f')
    }




    fun getColor(position: Char): Color {
        return iReadSettings.getColor(position)
    }




    fun selectPositionScreen(x: Char) {
        _selectedScreen.value = when (x) {
            '1' -> { { LocalSourceScreen(this) } }
            '2' -> { { ExternalSourceScreen( this) } }
            else -> { { LocalSourceScreen(this) } }
        }
    }
}