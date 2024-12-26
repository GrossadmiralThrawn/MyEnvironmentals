package com.myenvironmentals.viewmodels




import androidx.lifecycle.ViewModel
import com.myenvironmentals.models.settings.StandardSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class SettingViewModel(): ViewModel() {
    private val standardSettings      = StandardSettings()
    private val _notificationsEnabled = MutableStateFlow(true)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled
    private val _darkModeEnabled = MutableStateFlow(false)
    val darkModeEnabled: StateFlow<Boolean> = _darkModeEnabled



    fun getColorMode(): Char {
        return standardSettings.getColorMode()
    }






    fun toggleNotificationsEnabled(enabled: Boolean) {
        _notificationsEnabled.value = enabled
    }

    fun toggleDarkModeEnabled(enabled: Boolean) {
        _darkModeEnabled.value = enabled


        if (enabled)
        {
             standardSettings.switchToDarkMode()
        }
        else
        {
            standardSettings.switchToLightMode()
        }
    }
}