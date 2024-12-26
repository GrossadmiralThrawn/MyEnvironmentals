package com.myenvironmentals.viewmodels




import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myenvironmentals.models.Connections.IConnection
import kotlinx.coroutines.launch




class AddMicrocontrollerViewModel(private val connection: IConnection): ViewModel() {
    val fabColor = mutableStateOf(Color.Blue)
    val mainContentText = mutableStateOf("Main content")

    fun onFabClick() {
        viewModelScope.launch {//Do stuff with the interface.
        }
    }
}