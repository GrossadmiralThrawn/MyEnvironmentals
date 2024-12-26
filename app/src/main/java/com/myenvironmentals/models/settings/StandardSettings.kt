package com.myenvironmentals.models.settings




class StandardSettings: ISettings {
    private var lightMode  = false
    private var darkMode   = false
    private var systemMode = true

    override fun switchToLightMode() {
        lightMode  = true
        darkMode   = false
        systemMode = false
    }



    override fun switchToDarkMode(){
        darkMode   = true
        lightMode  = false
        systemMode = false
    }



    override fun switchToSystemMode() {
        systemMode = true
        lightMode  = false
        darkMode   = false
    }




    override fun getColorMode(): Char {
        if (systemMode == true)
            return 's'


        if (lightMode == true) {
            return 'l'
        }


        return 'd'
    }
}