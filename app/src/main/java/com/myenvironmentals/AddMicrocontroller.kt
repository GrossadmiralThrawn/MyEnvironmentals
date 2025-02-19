package com.myenvironmentals




import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.myenvironmentals.screens.ExternalSourceScreen
import com.myenvironmentals.screens.LocalSourceScreen
import com.myenvironmentals.screens.SelectConnectionSourceScreen
import com.myenvironmentals.ui.theme.MyEnvironmentalsTheme
import com.myenvironmentals.viewmodels.ConnectionViewModel




class AddMicrocontroller : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ConnectionViewModel(this)
        setContent {
            MyEnvironmentalsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AddController(viewModel, Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AddController(viewModel: ConnectionViewModel, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main", modifier = modifier) {
        composable("main") {
            SelectConnectionSourceScreen(connectionViewModel = viewModel, navController = navController)
        }
        composable("local") {
            LocalSourceScreen(viewModel = viewModel)
        }
        composable("external") {
            ExternalSourceScreen(viewModel = viewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddMicrocontrollerPreview() {
    MyEnvironmentalsTheme {
        AddController(ConnectionViewModel(context = LocalContext.current))
    }
}