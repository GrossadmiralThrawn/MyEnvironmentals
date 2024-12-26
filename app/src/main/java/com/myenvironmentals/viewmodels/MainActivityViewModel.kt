package com.myenvironmentals.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainActivityViewModel : ViewModel() {

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
    }
}
