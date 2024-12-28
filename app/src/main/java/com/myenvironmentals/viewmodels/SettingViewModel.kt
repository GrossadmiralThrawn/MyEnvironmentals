package com.myenvironmentals.viewmodels




import android.content.Context
import androidx.lifecycle.ViewModel
import com.myenvironmentals.models.settings.StandardSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow




class SettingViewModel(context: Context): ViewModel() {
    private val standardSettings                         = StandardSettings(context)
    private val _notificationsEnabled                    = MutableStateFlow(true)
    val notificationsEnabled:         StateFlow<Boolean> = _notificationsEnabled
    private val _darkModeEnabled                         = MutableStateFlow(false)
    val darkModeEnabled:              StateFlow<Boolean> = _darkModeEnabled
    private val _systemModeEnabled                       = MutableStateFlow(false)
    val systemModeEnabled:            StateFlow<Boolean> = _systemModeEnabled




    init {
        val colorMode = standardSettings.getColorMode()
        //_notificationsEnabled.value = standardSettings.switchNotification()

        if (colorMode == 's') {
            _systemModeEnabled.value = true
            _darkModeEnabled.value   = false
        }
        else {
            if (colorMode == 'd') {
                _systemModeEnabled.value = false
                _darkModeEnabled.value   = true
            } else {
                _systemModeEnabled.value = false
                _darkModeEnabled.value   = false
            }
        }
    }




    fun toggleNotificationsEnabled(enabled: Boolean) {
        _notificationsEnabled.value = enabled
    }




    fun toggleDarkModeEnabled(enabled: Boolean) {
        _darkModeEnabled.value   = enabled

        if (enabled)
        {
            standardSettings.switchToDarkMode()
        }
        else
        {
            standardSettings.switchToLightMode()
        }
    }




    fun systemModeEnabled(enabled: Boolean)
    {
        _systemModeEnabled.value = enabled


        if (enabled) {
            standardSettings.switchToSystemMode()
        }
        else
        {
            toggleDarkModeEnabled(darkModeEnabled.value) //Anderenfalls reaktiviert sich der Systemmodus so oft, bis man den Light-/ Darkmode manuell retogglet.
        }
    }
}