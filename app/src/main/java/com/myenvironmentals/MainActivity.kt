package com.myenvironmentals




import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.myenvironmentals.models.settings.StandardSettingsReader
import com.myenvironmentals.ui.theme.MyEnvironmentalsTheme
import com.myenvironmentals.ui.theme.Purple40
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
    val context = LocalContext.current
    val startSettingsActivity by viewModel.startSettingsActivityEvent.collectAsState()
    val startAddNewControllerActivity by viewModel.startAddNewControllerEvent.collectAsState()

    LaunchedEffect(startSettingsActivity) {
        if (startSettingsActivity) {
            context.startActivity(Intent(context, SettingsActivity::class.java))
            viewModel.resetActivityEvents()
        }
    }

    LaunchedEffect(startAddNewControllerActivity) {
        if (startAddNewControllerActivity) {
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
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(viewModel.getColor('b'))
        ) {
            // Main content in the center
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = "Hello, Android!", color = viewModel.getColor('f'))
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
                        .background(viewModel.getColor('b')),
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




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(viewModel: MainActivityViewModel) {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name),
            style = MaterialTheme.run { typography.headlineLarge.copy(fontWeight = FontWeight.Bold)},
            color = viewModel.getColor('f'))
        },

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