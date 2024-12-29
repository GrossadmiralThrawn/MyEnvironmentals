package com.myenvironmentals.viewmodels




import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.mutableStateOf
import com.myenvironmentals.models.settings.IReadSettings




class MainActivityViewModel (private val readSettings: IReadSettings): ViewModel() {
    // Zustand für das Dropdown-Menü
    var expanded = mutableStateOf(false)
        private set
    // StateFlow für das Event, das eine neue Activity starten soll
    private val _startNewActivityEvent = MutableStateFlow(false)
    val startNewActivityEvent: StateFlow<Boolean> = _startNewActivityEvent




    // Methode zum Umschalten des Dropdown-Menüs
    fun toggleMenu() {
        expanded.value = !expanded.value
    }





    //Methode, um eine neue Activity zu starten
    fun startNewActivity() {
        _startNewActivityEvent.value = true
    }




    //Zurücksetzen des Events
    fun resetActivityEvent() {
        _startNewActivityEvent.value = false
        reloadPreferences()
    }




    @Composable
    fun getTopBarBackgroundColor(): Color {
        val colorMode = readSettings.getColorMode()
        val colorSet  = readSettings.getColorSet()



        return when {
            (colorMode == 's' && isSystemInDarkTheme())  || colorMode =='d' -> colorSet[0] // Dark mode und System im Dark-Theme
            (colorMode == 'l' && !isSystemInDarkTheme()) || colorMode =='l'-> colorSet[1] // Light mode
            else -> colorSet[0] // Fallback (default dark mode)
        }
    }




    @Composable
    fun getBodyBackgroundColor(): Color
    {
        val colorMode = readSettings.getColorMode()
        val colorSet  = readSettings.getColorSet()



        return when {
            (colorMode == 's' && isSystemInDarkTheme())  || colorMode =='d' -> colorSet[2] // Dark mode und System im Dark-Theme
            (colorMode == 'l' && !isSystemInDarkTheme()) || colorMode =='l'-> colorSet[3] // Light mode
            else -> colorSet[2] // Fallback (default dark mode)
        }
    }




    @Composable
    fun getFontColor(): Color {
        val colorMode = readSettings.getColorMode()
        val colorSet  = readSettings.getColorSet()



        return when {
            (colorMode == 's' && isSystemInDarkTheme())  || colorMode =='d' -> colorSet[5] // Dark mode und System im Dark-Theme
            (colorMode == 'l' && !isSystemInDarkTheme()) || colorMode =='l'-> colorSet[4] // Light mode
            else -> colorSet[5] // Fallback (default dark mode)
        }
    }




    fun reloadPreferences()
    {
        readSettings.reloadPreferences()
    }
}