package com.myenvironmentals.viewmodels




import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.mutableStateOf
import com.myenvironmentals.models.settings.IReadSettings




class MainActivityViewModel (private val iReadSettings: IReadSettings): ViewModel() {
    // Zustand für das Dropdown-Menü
    var expanded = mutableStateOf(false)
        private set
    // StateFlow für das Event, das eine neue Activity starten soll
    private val _startSettingsActivityEvent            = MutableStateFlow(false)
    val startSettingsActivityEvent: StateFlow<Boolean> = _startSettingsActivityEvent
    private val _startAddNewControllerEvent            = MutableStateFlow(false)
    val startAddNewControllerEvent: StateFlow<Boolean> = _startAddNewControllerEvent




    // Methode zum Umschalten des Dropdown-Menüs
    fun toggleMenu() {
        expanded.value = !expanded.value
    }





    //Methode, welche den Event-Value togglet
    fun startSettingsActivity() {
        _startSettingsActivityEvent.value = true
    }




    fun startAddMicrocontrollerActivity() {
        _startAddNewControllerEvent.value = true
    }




    //Zurücksetzen des Events
    fun resetActivityEvents() {
        _startSettingsActivityEvent.value = false
        _startAddNewControllerEvent.value = false
    }




    @Composable
    fun getColor(position: Char): Color
    {
        return iReadSettings.getColor(position)
    }
}