package com.myenvironmentals.models.settings




import android.content.Context           //Nutzt den Kontext in dem sie erstellt wurde.
import android.content.SharedPreferences //Shared Preferences sind eine Möglichkeit in
                                         //Android Studio um Daten activityübergreifend
                                         //zu speichern




class StandardSettings (private val context: Context): ISettings {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)
    private var lightMode:         Boolean           = sharedPreferences.getBoolean("lightMode", false) //getBoolean ist eine Funktion, welche einen Defaultwert setzt, falls keiner in der aktuellen Datei vorhanden ist.
    private var darkMode:          Boolean           = sharedPreferences.getBoolean("darkMode", false)
    private var systemMode:        Boolean           = sharedPreferences.getBoolean("systemMode", true)




    init {
        loadSettings()
    }




    fun loadSettings() {
        lightMode = sharedPreferences.getBoolean("lightMode", false)
        darkMode = sharedPreferences.getBoolean("darkMode", false)
        systemMode = sharedPreferences.getBoolean("systemMode", true)
    }





    override fun switchToLightMode() {
        lightMode  = true
        darkMode   = false
        systemMode = false
        saveColorMode()
    }



    override fun switchToDarkMode(){
        darkMode   = true
        lightMode  = false
        systemMode = false
        saveColorMode()
    }



    override fun switchToSystemMode() {
        systemMode = true
        lightMode  = false
        darkMode   = false
        saveColorMode()
    }




    override fun getColorMode(): Char {
        return when {
            systemMode -> 's'
            lightMode -> 'l'
            darkMode -> 'd'
            else -> 's'
        }
    }




    private fun saveColorMode() {
        sharedPreferences.edit()
            .putBoolean("lightMode", lightMode)
            .putBoolean("darkMode", darkMode)
            .putBoolean("systemMode", systemMode)
            .apply()
    }




    override fun switchNotification(enabled: Boolean) {
        TODO("Not yet implemented")
    }
}