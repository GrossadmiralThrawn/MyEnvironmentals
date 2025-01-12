package com.myenvironmentals




import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.myenvironmentals.models.connections.IConnection
import com.myenvironmentals.models.settings.IReadSettings
import com.myenvironmentals.viewmodels.AddMicrocontrollerViewModel




@Composable
fun ConnectionScreen(addMicrocontrollerViewModel: AddMicrocontrollerViewModel)
{
    val fontColor by addMicrocontrollerViewModel.fontColor.collectAsState()
    val bodyColor by addMicrocontrollerViewModel.bodyColor.collectAsState()



    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().background(bodyColor)
    ) {
        Text(text = "Verbindung: ${addMicrocontrollerViewModel.getConnectionType()::class.simpleName}", color = fontColor)
    }
}