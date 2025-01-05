package com.myenvironmentals





import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import com.myenvironmentals.models.connections.WLANConnection
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myenvironmentals.models.settings.StandardSettingsReader
import com.myenvironmentals.ui.theme.MyEnvironmentalsTheme
import com.myenvironmentals.viewmodels.AddMicrocontrollerViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.text.font.FontWeight




class AddMicrocontroller : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyEnvironmentalsTheme {
                ConnectionTypeSelection(
                    this,
                    AddMicrocontrollerViewModel(StandardSettingsReader(this))
                )
            }
        }
    }
}




@Composable
fun ConnectionTypeSelection(context: Context, viewModel: AddMicrocontrollerViewModel) {
    val startAnimation by viewModel.startConnectionAnimationEvent.collectAsState()





    LaunchedEffect(startAnimation) {
        val intent = Intent(context, PlaceholderActivity::class.java)



        if (startAnimation) {
            val wlanConnection = WLANConnection()



            viewModel.setConnectionType(wlanConnection)
            context.startActivity(intent)
            viewModel.connectionAnimation()
        }
    }




    Scaffold(
        topBar = {
            AppTopBar(viewModel)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(viewModel.getColor('b')), // Correct background modifier usage
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            //Spacer für Abstände
            Spacer(modifier = Modifier.padding(16.dp))

            //Überschrift
            Text(
                text = stringResource(R.string.types_of_connection),
                style = MaterialTheme.run {
                    typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                },
                color = viewModel.getColor('f')
            )

            Spacer(modifier = Modifier.padding(32.dp))

            //Buttons mit Bildern umd die Verbindungsart auszuwählen.
            TextButton (
                onClick = {
                    viewModel.connectionAnimation()
                },
                modifier = Modifier
                    .background(viewModel.getColor('b')),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_network_wifi_3_bar_24), // Replace with your drawable resource
                    contentDescription = "Image Button",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(64.dp),
                    colorFilter = tint(viewModel.getColor('f')) // Wende die Farbe dynamisch an
                )
            }
            Text(text = "WLAN",
                color = viewModel.getColor('f'),
                modifier = Modifier.clickable {
                    viewModel.connectionAnimation()
                },
            )

            Spacer(modifier = Modifier.padding(16.dp))

            TextButton (
                onClick = {
                    viewModel.connectionAnimation()
                },
                modifier = Modifier
                    .background(viewModel.getColor('b')),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_contactless_24), // Replace with your drawable resource
                    contentDescription = "Image Button",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(64.dp),
                    colorFilter = tint(viewModel.getColor('f')) // Wende die Farbe dynamisch an
                )
            }
            Text(text = "NFC",
                color = viewModel.getColor('f'),
                modifier = Modifier.clickable {
                    viewModel.connectionAnimation()
                },
            )

            Spacer(modifier = Modifier.padding(16.dp))

            TextButton (
                onClick = {
                    viewModel.connectionAnimation()
                },
                modifier = Modifier
                    .background(viewModel.getColor('b')),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_bluetooth_24), // Replace with your drawable resource
                    contentDescription = "Image Button",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(64.dp),
                    colorFilter = tint(viewModel.getColor('f')) // Wende die Farbe dynamisch an
                )
            }
            Text(text = "Bluetooth",
                color = viewModel.getColor('f'),
                modifier = Modifier.clickable {
                    viewModel.connectionAnimation()
                },
            )
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(viewModel: AddMicrocontrollerViewModel)
{
    TopAppBar(
        title = {
            Text(
            stringResource(R.string.title_activity_add_microcontroller),
            style = MaterialTheme.run { typography.headlineLarge.copy(fontWeight = FontWeight.Bold) }
        ) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = viewModel.getColor('t'),
            titleContentColor = viewModel.getColor('f'),
            actionIconContentColor = viewModel.getColor('b'),
        )
    )
}










@Preview(showBackground = true)
@Composable
fun AddMicrocontrollerPreview() {
    MyEnvironmentalsTheme {
        ConnectionTypeSelection(
            LocalContext.current,
            AddMicrocontrollerViewModel
            (
                StandardSettingsReader(
                    LocalContext.current
                )
            )
        )
    }
}