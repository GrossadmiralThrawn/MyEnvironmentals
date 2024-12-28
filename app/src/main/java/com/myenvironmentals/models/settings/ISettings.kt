package com.myenvironmentals.models.settings

interface ISettings {
    fun switchToLightMode()
    fun switchToDarkMode()
    fun switchToSystemMode()
    /**
     * @return A function which should return l for light mode d for dark mode and s for system mode minimum.
     */
    fun getColorMode(): Char //A function which should return l for light mode d for dark mode and s for system mode minimum.
    fun switchNotification(enabled: Boolean)
}