package com.myenvironmentals




import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.myenvironmentals.models.connections.IConnection
import com.myenvironmentals.models.settings.IReadSettings
import com.myenvironmentals.viewmodels.AddMicrocontrollerViewModel




@Composable
fun ConnectionScreen(addMicrocontrollerViewModel: AddMicrocontrollerViewModel)
{
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Verbindung: ${addMicrocontrollerViewModel.getConnectionType()::class.simpleName}")
    }
}