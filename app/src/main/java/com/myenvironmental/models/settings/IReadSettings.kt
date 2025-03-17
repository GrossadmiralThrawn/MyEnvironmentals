package com.myenvironmental.models.settings




import androidx.compose.ui.graphics.Color




interface IReadSettings {
    fun getColorMode(): Char

    /**
     * @return an array of top bar dark, light, body dark, light, font dark, light.
     */
    fun getColorSet(): Array<Color>
    fun getNotificationAllowed(): Boolean
    fun reloadPreferences()

    /**
     * @param elementType is a character which can be t for top bar
     *        b for body
     *        f for font or
     *        m for menu
     *        e for else.
     * @return returns the color which should be used there
     */
    fun getColor(elementType: Char): Color
}
