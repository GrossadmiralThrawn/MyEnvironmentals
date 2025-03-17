package com.myenvironmental.models.settings




import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.compose.ui.graphics.Color
import com.myenvironmental.ui.theme.Black
import com.myenvironmental.ui.theme.BodyDark
import com.myenvironmental.ui.theme.BodyLight
import com.myenvironmental.ui.theme.DropDownMenuDark
import com.myenvironmental.ui.theme.DropDownMenuLight
import com.myenvironmental.ui.theme.Purple700
import com.myenvironmental.ui.theme.TopBarDark
import com.myenvironmental.ui.theme.TopBarLight
import com.myenvironmental.ui.theme.White




class StandardSettingReader (private val context: Context): IReadSettings
{
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)
    private var lightMode: Boolean = sharedPreferences.getBoolean(
        "lightMode",
        false
    ) //getBoolean ist eine Funktion, welche einen Defaultwert setzt, falls keiner in der aktuellen Datei vorhanden ist.
    private var darkMode: Boolean = sharedPreferences.getBoolean("darkMode", false)
    private var systemMode: Boolean = sharedPreferences.getBoolean("systemMode", true)




    init {
        loadSettings()
    }




    override fun getColorMode(): Char {
        val uiMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK



        return when {
            systemMode -> 's'
            darkMode -> 'r'
            lightMode -> 'l'
            uiMode == Configuration.UI_MODE_NIGHT_YES -> 'd'
            uiMode == Configuration.UI_MODE_NIGHT_NO -> 'l'
            else -> 's'
        }
    }


    override fun getColorSet(): Array<Color> {
        return arrayOf(TopBarDark, TopBarLight, BodyDark, BodyLight, Black, White)
    }


    override fun getColor(elementType: Char): Color {
        val colorMode = getColorMode()
        return when (elementType) {
            't' -> {
                when (colorMode) {
                    'l' -> TopBarLight
                    'd' -> TopBarDark
                    else -> TopBarDark
                }
            }

            'b' -> {
                when (colorMode) {
                    'd' -> BodyDark
                    'l' -> BodyLight
                    else -> BodyDark
                }
            }

            'f' -> {
                when (colorMode) {
                    'd' -> White
                    'l' -> Black
                    else -> White
                }
            }

            'm' -> {
                when (colorMode) {
                    'd' -> DropDownMenuDark
                    'l' -> DropDownMenuLight
                    else -> DropDownMenuDark
                }
            }

            else -> Purple700
        }
    }


    override fun getNotificationAllowed(): Boolean {
        TODO("Not yet implemented")
    }


    override fun reloadPreferences() {
        loadSettings()
    }


    private inline fun loadSettings() {
        lightMode = sharedPreferences.getBoolean("lightMode", false)
        darkMode = sharedPreferences.getBoolean("darkMode", false)
        systemMode = sharedPreferences.getBoolean("systemMode", true)
    }
}