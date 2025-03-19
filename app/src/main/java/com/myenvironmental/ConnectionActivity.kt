package com.myenvironmental




import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myenvironmental.models.connections.WiFiConnection
import com.myenvironmental.models.settings.StandardSettingReader
import com.myenvironmental.ui.theme.MyEnvironmentalTheme
import com.myenvironmental.viewmodels.ConnectionViewModel


class ConnectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyEnvironmentalTheme {
                Connect(
                    ConnectionViewModel(StandardSettingReader(context = this),
                    WiFiConnection(this), getString(R.string.connected), getString(R.string.connection_lost)))
            }
        }
    }
}




@Composable
fun Connect(connectionViewModel: ConnectionViewModel) {
    val bodyColor = connectionViewModel.bodyColor.collectAsState()
    val fontColor = connectionViewModel.fontColor.collectAsState()
    val connectionStatus = connectionViewModel.connectedToWiFi.collectAsState() // Verwende den richtigen Status-Text
    val isHighlighted = connectionViewModel.connectionColor.collectAsState() // Verwende den Status für die Farbe der Box


    
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(bodyColor.value)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .background(isHighlighted.value)
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(32.dp)// Maximal 10% des Displays für die farbige Box
                ) {
                    Text(
                        text = connectionStatus.value, // Zeigt den Status an
                        color = fontColor.value, // Farbe des Textes
                        modifier = Modifier.align(Alignment.Center) // Zentriert den Text
                    )
                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    MyEnvironmentalTheme {
        Connect(ConnectionViewModel(StandardSettingReader(LocalContext.current), WiFiConnection(LocalContext.current),
            LocalContext.current.getString(R.string.connected),
            LocalContext.current.getString(R.string.connection_lost))
        )
    }
}