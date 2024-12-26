package com.myenvironmentals.models.settings

interface ISettings {
    fun switchToLightMode()
    fun switchToDarkMode()
    fun switchToSystemMode()
    fun getColorMode(): Char //A function which should return l for light mode d for dark mode and s for system mode minimum.
}