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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myenvironmentals.ui.theme.BodyDark
import com.myenvironmentals.ui.theme.MyEnvironmentalsTheme
import com.myenvironmentals.ui.theme.TopBarDark
import com.myenvironmentals.ui.theme.White
import com.myenvironmentals.viewmodels.MainActivityViewModel




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyEnvironmentalsTheme {
                MainScreen()
            }
        }
    }
}




@Composable
fun MainScreen() {
    //ViewModel verwenden
    val viewModel: MainActivityViewModel = viewModel()
    //Beobachte das Event, um eine neue Activity zu starten
    val startNewActivity by viewModel.startNewActivityEvent.collectAsState()
    //Hole den Context, um die Activity zu starten
    val context = LocalContext.current



    // Wenn das Event ausgelöst wird, starte eine neue Activity
    LaunchedEffect(startNewActivity) {
        if (startNewActivity) {
            // Neue Activity starten
            context.startActivity(Intent(context, SettingsActivity::class.java))
            // Event zurücksetzen, um zu verhindern, dass es wiederholt ausgelöst wird
            viewModel.resetActivityEvent()
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
                .background(BodyDark), // Correct background modifier usage
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Hello, Android!", color = White)
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(viewModel: MainActivityViewModel) {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        actions = {
            // Search Icon
            IconButton(onClick = { /* Handle search action */ }) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
            // Dropdown Menu Icon
            Box {
                IconButton(onClick = { viewModel.toggleMenu() }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Options")
                }
                DropdownMenu(
                    expanded = viewModel.expanded.value,
                    onDismissRequest = { viewModel.toggleMenu() }
                ) {
                    DropdownMenuItem(
                        text = { Text("Settings") },
                        onClick = { viewModel.startNewActivity() } // Ruft die Methode auf, die das Event auslöst
                    )
                    DropdownMenuItem(
                        text = { Text("Option 2") },
                        onClick = { /* Handle action 2 */ }
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TopBarDark,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}




@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MyEnvironmentalsTheme {
        MainScreen()
    }
}
