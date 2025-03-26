package com.myenvironmental.models.connections.errors




import kotlinx.serialization.Serializable




@Serializable
data class ResultText(
    val result:        String  = "",
    val controllerID:  String? = "",
    val framePriority: Byte?   = 0,
    val temperature:   Float?  = 0.0f,
    val humidity:      Float?  = 0.0f,
    val pressure:      Float?  = 0.0f,
    val uvIndex:       Float?  = 0.0f,
    val light:         Float?  = 0.0f,
)