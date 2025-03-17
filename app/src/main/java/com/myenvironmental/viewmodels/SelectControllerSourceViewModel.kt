package com.myenvironmental.viewmodels




import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.myenvironmental.models.settings.IReadSettings
import com.myenvironmental.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject




@HiltViewModel
class SelectControllerSourceViewModel @Inject constructor(private val iReadSettings: IReadSettings): ViewModel() {
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





    fun getColor(position: Char): Color
    {
        return iReadSettings.getColor(position)
    }
}