package com.myenvironmentals.screens




import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.myenvironmentals.models.connections.IConnection
import com.myenvironmentals.viewmodels.ConnectionViewModel




@Composable
fun LocalSourceScreen(viewModel: ConnectionViewModel) {
    val fontColor by viewModel.fontColor.collectAsState()
    val bodyColor by viewModel.bodyColor.collectAsState()


    Column(
        modifier = Modifier
            .padding()
            .fillMaxSize()
            .background(bodyColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hallo Welt", color = fontColor)
    }

}
