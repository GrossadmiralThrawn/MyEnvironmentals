package com.myenvironmental.models.settings




import androidx.compose.ui.graphics.Color




interface ISettings : IReadSettings {
    fun switchToLightMode()
    fun switchToDarkMode()
    fun switchToSystemMode()

    /**
     * @return A function which should return l for light mode d for dark mode and s for system mode minimum.
     */
    override fun getColorMode(): Char
    override fun getColorSet(): Array<Color>
    fun switchNotification(enabled: Boolean)
}
