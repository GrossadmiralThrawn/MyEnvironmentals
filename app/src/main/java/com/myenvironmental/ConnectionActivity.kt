package com.myenvironmental




import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
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
                Connect(ConnectionViewModel(StandardSettingReader(context = this), WiFiConnection(this)))
            }
        }
    }
}




@Composable
fun Connect(connectionViewModel: ConnectionViewModel){
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    MyEnvironmentalTheme {
        Connect(ConnectionViewModel(StandardSettingReader(LocalContext.current), WiFiConnection(LocalContext.current)))
    }
}