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
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myenvironmental.models.connections.WiFiConnection
import com.myenvironmental.models.settings.StandardSettingReader
import com.myenvironmental.ui.theme.MyEnvironmentalTheme
import com.myenvironmental.viewmodels.ConnectionViewModel
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.launch




class ConnectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyEnvironmentalTheme {
                Connect(
                    ConnectionViewModel(StandardSettingReader(context = this),
                    WiFiConnection(this, engine = OkHttp.create(), "https://www.purgomalum.com/service/json"
                    ), getString(R.string.connected), getString(R.string.connection_lost)))
            }
        }
    }
}




@Composable
fun Connect(connectionViewModel: ConnectionViewModel) {
    val bodyColor = connectionViewModel.bodyColor.collectAsState()
    val fontColor = connectionViewModel.fontColor.collectAsState()
    val connectionStatus = connectionViewModel.connectedToWiFi.collectAsState()
    val isHighlighted = connectionViewModel.connectionColor.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var serverResponse by remember { mutableStateOf("") }



    Box(
        modifier = Modifier.fillMaxSize().background(bodyColor.value),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxSize()
                .background(bodyColor.value)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                    .background(isHighlighted.value)
                    .fillMaxWidth()
                    .height(32.dp))
                Box(
                    modifier = Modifier
                        .background(isHighlighted.value)
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(32.dp)
                ) {
                    Text(
                        text = connectionStatus.value,
                        color = fontColor.value,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Button(onClick = {
                    coroutineScope.launch {
                        serverResponse = connectionViewModel.fetchDataFromServer("Fuck World")
                    }
                }) {
                    Text("Send Request")
                }

                Text(text = "Server Response: $serverResponse", modifier = Modifier.padding(16.dp), color = fontColor.value)
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun ConnectPreview() {
    MyEnvironmentalTheme {
        Connect(ConnectionViewModel(StandardSettingReader(LocalContext.current), WiFiConnection(LocalContext.current, engine = OkHttp.create(), "https://www.purgomalum.com/service/json"),
            LocalContext.current.getString(R.string.connected),
            LocalContext.current.getString(R.string.connection_lost),
        )
        )
    }
}