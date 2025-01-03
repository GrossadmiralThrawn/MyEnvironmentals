package com.myenvironmentals.viewmodels




import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.myenvironmentals.models.connections.IConnection
import com.myenvironmentals.models.settings.IReadSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow




class AddMicrocontrollerViewModel(private val iReadSettings: IReadSettings): ViewModel() {
    private val _startConnectionAnimationEvent            = MutableStateFlow(false)
    val startConnectionAnimationEvent: StateFlow<Boolean> = _startConnectionAnimationEvent




    fun getColor(position: Char): Color {
        return iReadSettings.getColor(position)
    }




    fun connectionAnimation()
    {
        _startConnectionAnimationEvent.value = !_startConnectionAnimationEvent.value
    }




    fun setConnectionType(iConnection: IConnection)
    {

    }
}