package com.myenvironmental




import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myenvironmental.ui.theme.MyEnvironmentalTheme
import com.myenvironmental.viewmodels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint




class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val settingsViewModel = SettingsViewModel(this)
        setContent {
            MyEnvironmentalTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SettingsActivityUI(
                        modifier = Modifier.padding(innerPadding), settingViewModel = settingsViewModel
                    )
                }
            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(settingViewModel: SettingsViewModel) {
    TopAppBar(
        title = {
            Text(
                stringResource(R.string.settings),
                style = MaterialTheme.run { typography.headlineLarge.copy(fontWeight = FontWeight.Bold) }
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = settingViewModel.getBackgroundColorTopBar(),
            titleContentColor = settingViewModel.getFontColor(),
            actionIconContentColor = Color.White
        )
    )
}




@Composable
fun SettingToggle(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit, enabled: Boolean, settingViewModel: SettingsViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = typography.bodyMedium.copy(fontSize = 20.sp),
            color = settingViewModel.getFontColor()
        )
        Switch(checked = checked, onCheckedChange = onCheckedChange, enabled = enabled)
    }
}




@Composable
fun Checkbox(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit, settingViewModel: SettingsViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = typography.bodyMedium.copy(fontSize = 20.sp),
            color = settingViewModel.getFontColor()
        )
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
    }
}




@Composable
fun SettingsActivityBody(settingViewModel: SettingsViewModel) {
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
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center // Zentriert den Inhalt horizontal
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
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = stringResource(R.string.primary_settings),
            style = typography.headlineMedium,
            color =  settingViewModel.getFontColor()
        )

        //Togglemenü für die Benachrichtigungen
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
            style = typography.headlineMedium,
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
fun SettingsActivityUI(modifier: Modifier, settingViewModel: SettingsViewModel) {
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
fun SettingsActivityPreview() {
    val context = LocalContext.current
    val viewModel = SettingsViewModel(context)
    MyEnvironmentalTheme {
        SettingsActivityUI(modifier = Modifier, viewModel)
    }
}
