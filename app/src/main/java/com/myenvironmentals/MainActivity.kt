package com.myenvironmentals




import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.myenvironmentals.models.settings.StandardSettingsReader
import com.myenvironmentals.ui.theme.MyEnvironmentalsTheme
import com.myenvironmentals.viewmodels.MainActivityViewModel




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val standardSettingsReader = StandardSettingsReader(this)
        //ViewModel verwenden
        val viewModel: MainActivityViewModel = MainActivityViewModel(StandardSettingsReader(this))


        setContent {
            MyEnvironmentalsTheme {
                MainScreen(standardSettingsReader, viewModel)
            }
        }
    }




    //Funktionm, die die UI updatet, wenn von einer anderen Activity zurückkommt.
    override fun onResume() {
        super.onResume()
        setContent{
            MyEnvironmentalsTheme {
                val standardSettingsReader = StandardSettingsReader(this)
                MainScreen(standardSettingsReader, MainActivityViewModel(standardSettingsReader))
            }
        }
    }
}




@Composable
fun MainScreen(standardSettingsReader: StandardSettingsReader, viewModel: MainActivityViewModel) {
    //Hole den Context, um die Activity zu starten
    val context = LocalContext.current
    //Beobachte das Event, um eine neue Activity zu starten
    val startSettingsActivity         by viewModel.startSettingsActivityEvent.collectAsState()
    val startAddNewControllerActivity by viewModel.startAddNewControllerEvent.collectAsState()



    //Wenn das Event ausgelöst wird, starte eine neue Activity
    LaunchedEffect(startSettingsActivity) {
        if (startSettingsActivity) {
            //Neue Activity starten
            context.startActivity(Intent(context, SettingsActivity::class.java))
            // Event zurücksetzen, um zu verhindern, dass es wiederholt ausgelöst wird
            viewModel.resetActivityEvents()
        }
    }



    LaunchedEffect(startAddNewControllerActivity) {
        if (startAddNewControllerActivity)
        {
            context.startActivity(Intent(context, AddMicrocontroller::class.java))
            viewModel.resetActivityEvents()
        }
    }



    Scaffold(
        topBar = {
            AppTopBar(viewModel)
        },
        modifier = Modifier.fillMaxSize(),

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(viewModel.getBodyBackgroundColor()), // Correct background modifier usage
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Hello, Android!", color = viewModel.getFontColor())
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(viewModel: MainActivityViewModel) {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name), color = viewModel.getFontColor()) },
        actions = {
            IconButton(onClick = { /* Handle search action */ }) {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = viewModel.getFontColor())
            }
            Box {
                IconButton(onClick = { viewModel.toggleMenu() }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Options", tint = viewModel.getFontColor())
                }
                DropdownMenu(
                    expanded = viewModel.expanded.value,
                    onDismissRequest = { viewModel.toggleMenu() },
                    modifier = Modifier.background(viewModel.getBodyBackgroundColor())
                ) {
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.settings), color = viewModel.getFontColor()) },
                        onClick = {
                            viewModel.startSettingsActivity()
                            viewModel.toggleMenu()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.add_environmental), color = viewModel.getFontColor()) },
                        onClick = {
                            viewModel.startAddMicrocontrollerActivity()
                            viewModel.toggleMenu()
                        }
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = viewModel.getTopBarBackgroundColor(),
            titleContentColor = viewModel.getFontColor(),
            actionIconContentColor = viewModel.getFontColor()
        )
    )
}




@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MyEnvironmentalsTheme {
        val standardSettingsReader = StandardSettingsReader(LocalContext.current)
        //ViewModel verwenden
        val viewModel: MainActivityViewModel = MainActivityViewModel(StandardSettingsReader(LocalContext.current))

        MainScreen(standardSettingsReader, viewModel)
    }
}