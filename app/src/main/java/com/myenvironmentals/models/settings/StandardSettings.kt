package com.myenvironmentals.models.settings




import android.content.Context           //Nutzt den Kontext in dem sie erstellt wurde.
import android.content.SharedPreferences //Shared Preferences sind eine Möglichkeit in
import android.content.res.Configuration//Android Studio um Daten activityübergreifend
import androidx.compose.ui.graphics.Color
import com.myenvironmentals.ui.theme.Black
import com.myenvironmentals.ui.theme.BodyDark
import com.myenvironmentals.ui.theme.BodyLight
import com.myenvironmentals.ui.theme.TopBarDark
import com.myenvironmentals.ui.theme.TopBarLight
import com.myenvironmentals.ui.theme.White





class StandardSettings (private val context: Context): ISettings {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)
    private var lightMode:         Boolean           = sharedPreferences.getBoolean("lightMode", false) //getBoolean ist eine Funktion, welche einen Defaultwert setzt, falls keiner in der aktuellen Datei vorhanden ist.
    private var darkMode:          Boolean           = sharedPreferences.getBoolean("darkMode", false)
    private var systemMode:        Boolean           = sharedPreferences.getBoolean("systemMode", true)
    private var colorModeChecked:  Boolean           = sharedPreferences.getBoolean("colorModeChecked", false)




    init {
        if (colorModeChecked)
        {
            loadSettings()
            sharedPreferences.edit().putBoolean("colorModeChecked", true).apply()
        }
        else
        {
            val preSetting = getCurrentSystemColorMode()


            when (preSetting)
            {
                's' -> switchToSystemMode()
                'd' -> switchToDarkMode()
                'l' -> switchToLightMode()
            }



            colorModeChecked = true
            sharedPreferences.edit().putBoolean("colorModeChecked", true).apply()
        }
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




    private fun getCurrentSystemColorMode(): Char {
        val currentNightMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_YES -> 'd' //Dark mode
            Configuration.UI_MODE_NIGHT_NO -> 'l'  //Light mode
            else -> 's' // System default or undefined
        }
    }




    override fun getColorSet(): Array<Color> {
        return arrayOf(TopBarDark, TopBarLight, BodyDark, BodyLight, Black, White)
    }




    override fun getNotificationAllowed(): Boolean {
        TODO("Not yet implemented")
    }

    override fun reloadPreferences() {
        loadSettings()
    }
}