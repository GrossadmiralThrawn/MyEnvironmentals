package com.myenvironmental

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myenvironmental.models.settings.StandardSettingReader
import com.myenvironmental.ui.theme.MyEnvironmentalTheme
import com.myenvironmental.viewmodels.SelectControllerSourceViewModel




class SelectControllerSourceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel = SelectControllerSourceViewModel(StandardSettingReader(this)) // Assuming this viewModel initialization
            MyEnvironmentalTheme {
                SelectControllerSourceScreen(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarSelectSourceActivity(viewModel: SelectControllerSourceViewModel) {
    TopAppBar(
        title = {
            Text(
                stringResource(R.string.settings),
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = viewModel.topBarColor.collectAsState().value,
            titleContentColor = viewModel.fontColor.collectAsState().value,
            actionIconContentColor = Color.White
        )
    )
}

@Composable
fun SelectControllerSourceScreen(viewModel: SelectControllerSourceViewModel) {
    val context = LocalContext.current
    val bodyColor by viewModel.bodyColor.collectAsState()
    val fontColor by viewModel.fontColor.collectAsState()

    Scaffold(
        topBar = { TopBarSelectSourceActivity(viewModel) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(bodyColor), // bodyColor is assumed to be a Color
            verticalArrangement = Arrangement.Center, // Correct placement for verticalArrangement
            horizontalAlignment = Alignment.CenterHorizontally // Correct placement for horizontalAlignment

        ){
            ConnectionButton(
                iconRes = R.drawable.baseline_wifi_24,
                label = R.string.wlan,
                bodyColor = bodyColor,
                fontColor = fontColor,
                onClick = {
                    val intent = android.content.Intent(context, ConnectionActivity::class.java)
                    context.startActivity(intent)
                }
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun SelectControllerSourceScreenPreview() {
    MyEnvironmentalTheme {
        val viewModel = SelectControllerSourceViewModel(StandardSettingReader(LocalContext.current)) // Provide a mock ViewModel if needed
        SelectControllerSourceScreen(viewModel)
    }
}




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
