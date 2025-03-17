package com.myenvironmental.viewmodels




import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.myenvironmental.models.settings.IReadSettings
import com.myenvironmental.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject




/**
 * @sample ViewModel for the Settings-Activity. Colors are because of first try of MVVM hard-coded
 *         but are reachable about the Settings-Interface. Actually no need of dynamical color-request.
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor(private val iReadSettings: IReadSettings): ViewModel() {
    var expanded = mutableStateOf(false)
        private set
    // StateFlow für das Event, das eine neue Activity starten soll
    private val _startSettingsActivityEvent = MutableStateFlow(false)
    val startSettingsActivityEvent: StateFlow<Boolean> = _startSettingsActivityEvent
    private val _startAddNewControllerEvent = MutableStateFlow(false)
    val startAddNewControllerEvent: StateFlow<Boolean> = _startAddNewControllerEvent
    private val _topBarColor   = MutableStateFlow(TopBarDark)
    val topBarColor : StateFlow<Color>                 = _topBarColor
    private val _bodyColor     = MutableStateFlow(BodyDark)
    val bodyColor : StateFlow<Color>                   = _bodyColor
    private val _fontColor     = MutableStateFlow(White)
    val fontColor : StateFlow<Color>                   = _fontColor





    init {
        _topBarColor.value = this.getColor('t')
        _bodyColor.value   = this.getColor('b')
        _fontColor.value   = this.getColor('f')
    }




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





    fun getColor(position: Char): Color
    {
        return iReadSettings.getColor(position)
    }




    fun refreshState() {
        _topBarColor.value = getColor('t')
        _bodyColor.value = getColor('b')
        _fontColor.value = getColor('f')
        // Weitere Aktualisierungen, falls nötig
    }
}