package com.myenvironmental




import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myenvironmental.models.settings.StandardSettingReader
import com.myenvironmental.ui.theme.MyEnvironmentalTheme
import com.myenvironmental.viewmodels.MainActivityViewModel
import com.myenvironmental.viewmodels.SelectControllerSourceViewModel
import dagger.hilt.android.AndroidEntryPoint




class SelectControllerSourceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel = SelectControllerSourceViewModel(StandardSettingReader(this))
            MyEnvironmentalTheme {
                SelectControllerSourceScreen(viewModel)
            }
        }
    }
}




@Composable
fun SelectControllerSourceScreen (viewModel: SelectControllerSourceViewModel)
{
    val context = LocalContext.current  // Richtigen Context holen
    val bodyColor by viewModel.bodyColor.collectAsState()
    val fontColor by viewModel.fontColor.collectAsState()



    Box(
        modifier = Modifier.fillMaxSize().background(bodyColor),
        contentAlignment = Alignment.Center
    )
    {
        ConnectionButton(
            iconRes = R.drawable.baseline_wifi_24,
            label = R.string.wlan,
            bodyColor = bodyColor,
            fontColor = fontColor,
            onClick = {
                val intent = android.content.Intent(context, ConnectionActivity::class.java)
                context.startActivity(intent) // Context ist jetzt korrekt!
            }
        )
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyEnvironmentalTheme {
        SelectControllerSourceScreen(SelectControllerSourceViewModel(StandardSettingReader(LocalContext.current)))
    }
}




//Wiederverwendbarer Composable für die Verbindungstyp-Buttons
@Composable
fun ConnectionButton(
    iconRes: Int,
    label: Int,
    fontColor: Color,
    bodyColor: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(
            onClick = onClick,
            modifier = Modifier.background(bodyColor)
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = stringResource(id = label),
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(64.dp),
                colorFilter = tint(fontColor)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(id = label),
            color = fontColor
        )
    }
}