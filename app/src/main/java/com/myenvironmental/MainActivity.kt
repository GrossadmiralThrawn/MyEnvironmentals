package com.myenvironmental




import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myenvironmental.models.settings.*
import com.myenvironmental.ui.theme.MyEnvironmentalTheme
import com.myenvironmental.ui.theme.*
import com.myenvironmental.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint




@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: MainActivityViewModel = viewModel()

            MyEnvironmentalTheme {
                MainScreen(viewModel)
            }
        }
    }




    //Funktion, die die UI updatet, wenn von einer anderen Activity zurückkommt.
    override fun onResume() {
        super.onResume()
        setContent{
            MyEnvironmentalTheme {
                val standardSettingsReader = StandardSettingReader(this)
                MainScreen(MainActivityViewModel(standardSettingsReader))
            }
        }
    }
}




@Composable
fun MainScreen(viewModel: MainActivityViewModel) {
    val context = LocalContext.current
    val startSettingsActivity by viewModel.startSettingsActivityEvent.collectAsState()
    val startAddNewControllerActivity by viewModel.startAddNewControllerEvent.collectAsState()
    val fontColor by viewModel.fontColor.collectAsState()
    val bodyColor by viewModel.bodyColor.collectAsState()



    //Starteffekt für die Einstellungen (startet die Activity)
    LaunchedEffect(startSettingsActivity) {
        if (startSettingsActivity) {
            context.startActivity(Intent(context, SettingsActivity::class.java))
            viewModel.resetActivityEvents()
        }
    }

    //Starteffekt zum Hinzufügen von Microcontrollern (startet die Activity)
    LaunchedEffect(startAddNewControllerActivity) {
        if (startAddNewControllerActivity) {
            context.startActivity(Intent(context, SelectControllerSourceActivity::class.java))
            viewModel.resetActivityEvents()
        }
    }


    //"Körper" der UI, ähnlich zu Column oder Row.
    /*Standardisierte Struktur: Scaffold ist speziell für Layouts konzipiert,
      die wiederkehrende UI-Elemente haben, wie z. B. eine TopAppBar, BottomBar,
      oder Snackbar. Es sorgt dafür, dass diese Elemente richtig positioniert sind
      und der übrige verfügbare Platz sinnvoll genutzt wird.
      Anpassung der Abstände: Es berücksichtigt automatisch die inneren Abstände
      (innerPadding), damit der Inhalt nicht mit der TopBar, NavigationBar oder anderen
      Komponenten kollidiert.
      Integration von Material Design: Scaffold ist Teil von MaterialTheme und erleichtert
      dir die Implementierung von Material-Komponenten. */
    Scaffold(
        topBar = {
            AppTopBar(viewModel)
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(viewModel.getColor('b'))
        ) {
            // Main content in the center, everything is setted under the previous element (vertial)
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = "Hello, Android!", color = fontColor)
            }

            // Button in the bottom-right corner
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                TextButton(
                    onClick = {
                        viewModel.startAddMicrocontrollerActivity()
                    },
                    modifier = Modifier
                        .background(bodyColor),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_add_circle_outline_24),
                        contentDescription = "Image Button",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(64.dp),
                        colorFilter = tint(Purple40)
                    )
                }
            }
        }
    }
}




//Kümmert sich um die TopBarApp
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(viewModel: MainActivityViewModel) {
    val topBarColor by viewModel.topBarColor.collectAsState()
    val fontColor   by viewModel.fontColor.collectAsState()



    TopAppBar(
        title = { Text(stringResource(R.string.app_name),
            style = MaterialTheme.run { typography.headlineLarge.copy(fontWeight = FontWeight.Bold)}, //Kümmert sich um die Schriftart.
            color = fontColor)
        },

        //Actions, inklusive des Dropdownmenü
        actions = {
            Box {
                IconButton(onClick = { viewModel.toggleMenu() }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Options", tint = viewModel.getColor('f'))
                }
                DropdownMenu(
                    expanded = viewModel.expanded.value,
                    onDismissRequest = { viewModel.toggleMenu() },
                    modifier = Modifier.background(viewModel.getColor('m'))
                ) {
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.settings), color = viewModel.getColor('f')) },
                        onClick = {
                            viewModel.startSettingsActivity()
                            viewModel.toggleMenu()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.add_environmental), color = viewModel.getColor('f')) },
                        onClick = {
                            viewModel.startAddMicrocontrollerActivity()
                            viewModel.toggleMenu()
                        }
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = topBarColor,
            titleContentColor = fontColor,
            actionIconContentColor = fontColor
        )
    )
}




@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MyEnvironmentalTheme {
        StandardSettingReader(LocalContext.current)
        //ViewModel verwenden
        val viewModel = MainActivityViewModel(StandardSettingReader(LocalContext.current))

        MainScreen(viewModel)
    }
}