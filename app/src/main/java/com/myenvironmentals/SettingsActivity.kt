package com.myenvironmentals





import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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




/**
 * @sample Function, which takes care of the TopBar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar()
{
    TopAppBar(title = { Text(stringResource(R.string.app_name) + " ->  " + stringResource(R.string.settings)) })
}




@Composable
fun SettingToggle(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit, enabled: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyMedium)
        Switch(checked = checked, onCheckedChange = onCheckedChange, enabled = enabled)
    }
}









@Composable
fun Checkbox(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyMedium)
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
    }
}




/**
 * @sample Function, which takes care of the body/ anything which isn't the TopBar
 */
@Composable
fun SettingsActivityBody(settingViewModel: SettingViewModel) {
    val notificationsEnabled by settingViewModel.notificationsEnabled.collectAsState()
    val darkModeEnabled      by settingViewModel.darkModeEnabled.collectAsState()
    val systemModeEnabled    by settingViewModel.systemModeEnabled.collectAsState()



    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = stringResource(R.string.settings), style = MaterialTheme.typography.headlineLarge)

        // Notifications Toggle
        SettingToggle(
            title = "Enable Notifications",
            checked = notificationsEnabled,
            onCheckedChange = { settingViewModel.toggleNotificationsEnabled(it)},
            true
        )

        // Dark Mode Toggle
        SettingToggle(
            title = "Enable Dark Mode",
            checked = darkModeEnabled,
            onCheckedChange = { settingViewModel.toggleDarkModeEnabled(it) },
            enabled = !systemModeEnabled
        )

        Checkbox(title = "System Mode",
            checked = systemModeEnabled,
            onCheckedChange = { settingViewModel.systemModeEnabled(it) } )
    }
}



@Composable
fun SettingsActivityUI(modifier: Modifier, settingViewModel: SettingViewModel)
{
    Scaffold(
        topBar = { TopAppBar() },
        modifier = Modifier.fillMaxSize()
    )
    {
        innerPadding -> Column (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            SettingsActivityBody(settingViewModel)
        }
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val context = LocalContext.current
    val viewModel = SettingViewModel(context) // Verwenden des lokalen Kontexts
    MyEnvironmentalsTheme {
        SettingsActivityUI(modifier = Modifier, viewModel)
    }
}
