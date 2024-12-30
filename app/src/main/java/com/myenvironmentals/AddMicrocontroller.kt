package com.myenvironmentals





import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.myenvironmentals.models.connections.WLANConnection
import com.myenvironmentals.models.settings.StandardSettingsReader
import com.myenvironmentals.ui.theme.MyEnvironmentalsTheme
import com.myenvironmentals.viewmodels.AddMicrocontrollerViewModel




class AddMicrocontroller : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyEnvironmentalsTheme {
                ConnectionTypeSelection(
                    AddMicrocontrollerViewModel(WLANConnection(), StandardSettingsReader(this)),
                    name = "Android",
                    modifier = Modifier.padding()
                )
            }
        }
    }
}




@Composable
fun ConnectionTypeSelection(viewModel: AddMicrocontrollerViewModel, name: String, modifier: Modifier = Modifier) {
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
                .background(viewModel.getBodyColor()), // Correct background modifier usage
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Hello, Android!", color = viewModel.getFontColor())
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(viewModel: AddMicrocontrollerViewModel)
{
    TopAppBar(
        title = { Text(stringResource(R.string.app_name) + " -> " + stringResource(R.string.title_activity_add_microcontroller)) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = viewModel.getTopBarColor(),
            titleContentColor = viewModel.getFontColor(),
            actionIconContentColor = viewModel.getFontColor()
        )
    )
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    MyEnvironmentalsTheme {
        ConnectionTypeSelection(AddMicrocontrollerViewModel(WLANConnection(), StandardSettingsReader(
            LocalContext.current
        )), "Android")
    }
}