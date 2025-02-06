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
import com.myenvironmentals.ui.theme.MyEnvironmentalsTheme
import com.myenvironmentals.viewmodels.ConnectionViewModel




class AddMicrocontroller : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: ConnectionViewModel = ConnectionViewModel(this)
        enableEdgeToEdge()
        setContent {
            MyEnvironmentalsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AddController(
                        viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}




@Composable
fun AddController(viewModel: ConnectionViewModel, modifier: Modifier = Modifier) {
    SelectConnectionSourceScreen(viewModel)
}




@Preview(showBackground = true)
@Composable
fun AddMicrocontrollerPreview() {
    MyEnvironmentalsTheme {
        AddController(ConnectionViewModel(context = LocalContext.current))
    }
}