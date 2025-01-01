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
        //ViewModel verwenden
        val viewModel: MainActivityViewModel = MainActivityViewModel(StandardSettingsReader(this))


        setContent {
            MyEnvironmentalsTheme {
                MainScreen(viewModel)
            }
        }
    }




    //Funktionm, die die UI updatet, wenn von einer anderen Activity zurückkommt.
    override fun onResume() {
        super.onResume()
        setContent{
            MyEnvironmentalsTheme {
                val standardSettingsReader = StandardSettingsReader(this)
                MainScreen(MainActivityViewModel(standardSettingsReader))
            }
        }
    }
}




@Composable
fun MainScreen(viewModel: MainActivityViewModel) {
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
                .background(viewModel.getColor('b')), // Correct background modifier usage
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Hello, Android!", color = viewModel.getColor('f'))
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(viewModel: MainActivityViewModel) {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name), color = viewModel.getColor('f')) },
        actions = {
            IconButton(onClick = { /* Handle search action */ }) {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = viewModel.getColor('f'))
            }
            Box {
                IconButton(onClick = { viewModel.toggleMenu() }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Options", tint = viewModel.getColor('f'))
                }
                DropdownMenu(
                    expanded = viewModel.expanded.value,
                    onDismissRequest = { viewModel.toggleMenu() },
                    modifier = Modifier.background(viewModel.getColor('b'))
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
            containerColor = viewModel.getColor('t'),
            titleContentColor = viewModel.getColor('f'),
            actionIconContentColor = viewModel.getColor('f')
        )
    )
}




@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MyEnvironmentalsTheme {
        StandardSettingsReader(LocalContext.current)
        //ViewModel verwenden
        val viewModel: MainActivityViewModel = MainActivityViewModel(StandardSettingsReader(LocalContext.current))

        MainScreen(viewModel)
    }
}