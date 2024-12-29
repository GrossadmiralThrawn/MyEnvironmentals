package com.myenvironmentals.models.settings




import androidx.compose.ui.graphics.Color




interface IReadSettings {
    fun getColorMode(): Char
    /**
     * @return an array of top bar dark, light, body dark, light, font dark, light.
     */
    fun getColorSet(): Array<Color>
    fun getNotificationAllowed(): Boolean
    fun reloadPreferences()
}