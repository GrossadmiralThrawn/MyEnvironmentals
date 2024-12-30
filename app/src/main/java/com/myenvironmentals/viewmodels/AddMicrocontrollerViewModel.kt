package com.myenvironmentals.viewmodels




import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.myenvironmentals.models.connections.IConnection
import com.myenvironmentals.models.settings.IReadSettings




class AddMicrocontrollerViewModel(private val connection: IConnection, private val iReadSettings: IReadSettings): ViewModel() {
    private val colorSet: Array<Color> = iReadSettings.getColorSet()
    private val colorMode: Char        = iReadSettings.getColorMode()




    fun getTopBarColor(): Color {
        return when (colorMode)
        {
            'd'  -> colorSet[0]
            'l'  -> colorSet[1]
            else -> colorSet[0]
        }
    }




    fun getBodyColor(): Color {
        return when (colorMode)
        {
            'd'  -> colorSet[2]
            'l'  -> colorSet[3]
            else -> colorSet[2]
        }
    }




    fun getFontColor(): Color {
        return when (colorMode)
        {
            'd'  -> colorSet[5]
            'l'  -> colorSet[4]
            else -> colorSet[5]
        }
    }
}