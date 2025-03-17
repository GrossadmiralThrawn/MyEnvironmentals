package com.myenvironmental.viewmodels




import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.myenvironmental.models.settings.StandardSettings
import com.myenvironmental.ui.theme.Black
import com.myenvironmental.ui.theme.BodyDark
import com.myenvironmental.ui.theme.BodyLight
import com.myenvironmental.ui.theme.White
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow




class SettingsViewModel(context: Context): ViewModel() {
    private val standardSettings = StandardSettings(context)
    private val _notificationsEnabled = MutableStateFlow(true)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled
    private val _darkModeEnabled = MutableStateFlow(false)
    val darkModeEnabled: StateFlow<Boolean> = _darkModeEnabled
    private val _systemModeEnabled = MutableStateFlow(false)
    val systemModeEnabled: StateFlow<Boolean> = _systemModeEnabled


    init {
        val colorMode = standardSettings.getColorMode()
        //_notificationsEnabled.value = standardSettings.switchNotification()

        if (colorMode == 's') {
            _systemModeEnabled.value = true
            _darkModeEnabled.value = false
        } else {
            if (colorMode == 'd') {
                _systemModeEnabled.value = false
                _darkModeEnabled.value = true
            } else {
                _systemModeEnabled.value = false
                _darkModeEnabled.value = false
            }
        }
    }


    fun toggleNotificationsEnabled(enabled: Boolean) {
        _notificationsEnabled.value = enabled
    }


    fun toggleDarkModeEnabled(enabled: Boolean) {
        _darkModeEnabled.value = enabled

        if (enabled) {
            standardSettings.switchToDarkMode()
        } else {
            standardSettings.switchToLightMode()
        }
    }


    fun systemModeEnabled(enabled: Boolean) {
        _systemModeEnabled.value = enabled


        if (enabled) {
            standardSettings.switchToSystemMode()
        } else {
            toggleDarkModeEnabled(darkModeEnabled.value) //Anderenfalls reaktiviert sich der Systemmodus so oft, bis man den Light-/ Darkmode manuell retogglet.
        }
    }


    @Composable
    fun getBackgroundColorTopBar(): Color {
        return standardSettings.getColor('t')
    }


    @Composable
    fun getBackgroundColorBody(): Color {
        val systemModeEnabled by this.systemModeEnabled.collectAsState()
        val darkModeEnabled by this.darkModeEnabled.collectAsState()



        return when {
            systemModeEnabled && isSystemInDarkTheme() -> BodyDark   //Dark mode in System Mode
            systemModeEnabled && !isSystemInDarkTheme() -> BodyLight //Light mode in system Mode
            darkModeEnabled -> BodyDark // Explicit Dark Mode
            else -> BodyLight // Light Mode
        }
    }


    @Composable
    fun getFontColor(): Color {
        val systemModeEnabled by this.systemModeEnabled.collectAsState()
        val darkModeEnabled by this.darkModeEnabled.collectAsState()



        return when {
            systemModeEnabled && isSystemInDarkTheme() -> White   //Dark mode in System Mode
            systemModeEnabled && !isSystemInDarkTheme() -> Black //Light mode in system Mode
            darkModeEnabled -> White // Explicit Dark Mode
            else -> Black // Light Mode
        }
    }
}
