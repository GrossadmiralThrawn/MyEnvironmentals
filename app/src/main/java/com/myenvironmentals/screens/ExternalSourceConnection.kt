package com.myenvironmentals.screens




import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.myenvironmentals.viewmodels.ConnectionViewModel




@Composable
fun ExternalSourceScreen(viewModel: ConnectionViewModel)
{
    val fontColor by viewModel.fontColor.collectAsState()
    val bodyColor by viewModel.bodyColor.collectAsState()


    Box(
        Modifier.background(bodyColor)
    )
    {
        Text(text = "Hallo Welt", color = fontColor)
    }
}