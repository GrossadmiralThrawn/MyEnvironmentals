package com.myenvironmentals.viewmodels




import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.myenvironmentals.models.settings.IReadSettings




class AddMicrocontrollerViewModel(private val iReadSettings: IReadSettings): ViewModel() {
    private val colorSet: Array<Color> = iReadSettings.getColorSet()
    private val colorMode: Char        = iReadSettings.getColorMode()




    fun getColor(position: Char): Color {
        return iReadSettings.getColor(position)
    }
}