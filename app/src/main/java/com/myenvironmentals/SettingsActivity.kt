package com.myenvironmentals




import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myenvironmentals.ui.theme.MyEnvironmentalsTheme
import com.myenvironmentals.viewmodels.SettingViewModel




class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val settingViewModel = SettingViewModel(this)
        setContent {
            MyEnvironmentalsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SettingsActivityUI(
                        modifier = Modifier.padding(innerPadding), settingViewModel
                    )
                }
            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(settingViewModel: SettingViewModel) {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name) + " -> " + stringResource(R.string.settings)) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = settingViewModel.getBackgroundColorTopBar(),
            titleContentColor = settingViewModel.getFontColor(),
            actionIconContentColor = Color.White
        )
    )
}




@Composable
fun SettingToggle(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit, enabled: Boolean, settingViewModel: SettingViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 20.sp),
            color = settingViewModel.getFontColor()
        )
        Switch(checked = checked, onCheckedChange = onCheckedChange, enabled = enabled)
    }
}




@Composable
fun Checkbox(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit, settingViewModel: SettingViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 20.sp),
            color = settingViewModel.getFontColor()
        )
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
    }
}




@Composable
fun SettingsActivityBody(settingViewModel: SettingViewModel) {
    val notificationsEnabled by settingViewModel.notificationsEnabled.collectAsState()
    val darkModeEnabled by settingViewModel.darkModeEnabled.collectAsState()
    val systemModeEnabled by settingViewModel.systemModeEnabled.collectAsState()



    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.settings),
            style = MaterialTheme.run {
                typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
            },
            color = settingViewModel.getFontColor()
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = "Primary Settings",
            style = MaterialTheme.typography.headlineMedium,
            color =  settingViewModel.getFontColor()
        )

        SettingToggle(
            title = stringResource(R.string.enable_notifications),
            checked = notificationsEnabled,
            onCheckedChange = { settingViewModel.toggleNotificationsEnabled(it) },
            true,
            settingViewModel
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Text(
            text = stringResource(R.string.theme),
            style = MaterialTheme.typography.headlineMedium,
            color =  settingViewModel.getFontColor()
        )

        SettingToggle(
            title = stringResource(R.string.enable_dark_mode),
            checked = darkModeEnabled,
            onCheckedChange = { settingViewModel.toggleDarkModeEnabled(it) },
            enabled = !systemModeEnabled,
            settingViewModel
        )

        Checkbox(
            title = stringResource(R.string.enable_system_mode),
            checked = systemModeEnabled,
            onCheckedChange = { settingViewModel.systemModeEnabled(it) },
            settingViewModel
        )
    }
}




@Composable
fun SettingsActivityUI(modifier: Modifier, settingViewModel: SettingViewModel) {
    Scaffold(
        topBar = { TopAppBar(settingViewModel) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(settingViewModel.getBackgroundColorBody()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SettingsActivityBody(settingViewModel)
        }
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val context = LocalContext.current
    val viewModel = SettingViewModel(context)
    MyEnvironmentalsTheme {
        SettingsActivityUI(modifier = Modifier, viewModel)
    }
}
