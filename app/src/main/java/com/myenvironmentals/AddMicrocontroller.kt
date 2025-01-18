@file:OptIn(ExperimentalSharedTransitionApi::class)




package com.myenvironmentals




import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.myenvironmentals.models.connections.NFCConnection
import com.myenvironmentals.models.connections.WLANConnection
import com.myenvironmentals.models.settings.StandardSettingsReader
import com.myenvironmentals.ui.theme.MyEnvironmentalsTheme
import com.myenvironmentals.viewmodels.AddMicrocontrollerViewModel




class AddMicrocontroller : ComponentActivity() {
    // Lifecycle-Methode, die aufgerufen wird, wenn die Activity erstellt wird
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Aktiviert Edge-to-Edge-Darstellung
        enableEdgeToEdge()

        // Setzt die Benutzeroberfläche mit Jetpack Compose
        setContent {
            // Wendet ein benutzerdefiniertes Design auf die gesamte Oberfläche an
            MyEnvironmentalsTheme {
                // Ruft die Haupt-Composable auf, die den gesamten Inhalt enthält
                ConnectionTypeSelection(
                    this,
                    AddMicrocontrollerViewModel(StandardSettingsReader(this))
                )
            }
        }
    }
}




// Haupt-UI-Element, das die Verbindungstypauswahl anzeigt
@Composable
fun ConnectionTypeSelection(context: Context, viewModel: AddMicrocontrollerViewModel) {
    // Beobachtet Änderungen in ViewModel-Daten
    val fontColor by viewModel.fontColor.collectAsState()
    val bodyColor by viewModel.bodyColor.collectAsState()
    val navigateToConnection by viewModel.navigateToConnectionScreen.collectAsState()



    // Prüft, ob die Navigation zu einer anderen Bildschirm erforderlich ist
    if (navigateToConnection != null) {
        viewModel.setConnectionType(navigateToConnection!!)
        ConnectionScreen(viewModel) // Zeigt den Verbindungsscreen an
        return //Beendet die Activity, wenn man von ConnectionScreen zurückkehrt.
    }



    // Scaffold ist ein Layout, das häufig für die Grundstruktur einer Seite verwendet wird
    Scaffold(
        topBar = { AppTopBar(viewModel) }, // Definiert die obere App-Leiste
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        // Vertikale Anordnung der Elemente auf der Seite
        Column(
            modifier = Modifier
                .padding(innerPadding) // Innenabstände
                .fillMaxSize() // Füllt die gesamte Größe
                .background(bodyColor), // Dynamische Hintergrundfarbe
            horizontalAlignment = Alignment.CenterHorizontally, // Zentriert horizontal
            verticalArrangement = Arrangement.Top // Beginnt oben
        ) {
            Spacer(modifier = Modifier.padding(16.dp)) // Abstandshalter


            //Überschrift für den Bildschirm
            Text(
                text = stringResource(R.string.types_of_connection),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = fontColor // Dynamische Schriftfarbe
            )


            Spacer(modifier = Modifier.padding(24.dp)) // Abstandshalter


            // Buttons für verschiedene Verbindungstypen
            ConnectionButton(
                iconRes = R.drawable.baseline_network_wifi_3_bar_24, // WLAN-Symbol
                label = "WLAN", // Beschriftung
                fontColor = fontColor, // Dynamische Schriftfarbe
                bodyColor = bodyColor, // Dynamische Hintergrundfarbe
                onClick = { viewModel.navigateToConnectionScreen(WLANConnection(context)) } //Aktion bei Klick
            )

            Spacer(modifier = Modifier.padding(16.dp)) // Abstandshalter

            // Weitere Verbindungstypen (NFC, Bluetooth, Cloud) werden ähnlich definiert
            ConnectionButton(
                iconRes = R.drawable.baseline_contactless_24,
                label = "NFC",
                fontColor = fontColor,
                bodyColor = bodyColor,
                onClick = { viewModel.navigateToConnectionScreen(NFCConnection())}
            )

            Spacer(modifier = Modifier.padding(16.dp))

            ConnectionButton(
                iconRes = R.drawable.baseline_bluetooth_24,
                label = "Bluetooth",
                fontColor = fontColor,
                bodyColor = bodyColor,
                onClick = { viewModel.navigateToConnectionScreen(NFCConnection())}
            )

            Spacer(modifier = Modifier.padding(16.dp))

            ConnectionButton(
                iconRes = R.drawable.baseline_cloud_queue_24,
                label = "Cloud",
                fontColor = fontColor,
                bodyColor = bodyColor,
                onClick = { viewModel.navigateToConnectionScreen(NFCConnection()) }
            )
        }
    }
}




//Definiert die obere Leiste der App
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(viewModel: AddMicrocontrollerViewModel) {
    // Beobachtet Änderungen in ViewModel-Daten
    val topBarColor by viewModel.topBarColor.collectAsState()
    val fontColor by viewModel.fontColor.collectAsState()
    val bodyColor by viewModel.bodyColor.collectAsState()



    // TopAppBar zeigt die Leiste am oberen Bildschirmrand
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.title_activity_add_microcontroller), // Titel
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = topBarColor, // Farbe der Leiste
            titleContentColor = fontColor, // Farbe des Titels
            actionIconContentColor = bodyColor // Farbe der Symbole
        )
    )
}




// Wiederverwendbare Composable für die Verbindungstyp-Buttons
@Composable
fun ConnectionButton(
    iconRes: Int, // Ressourcen-ID des Symbols
    label: String, // Beschriftung
    fontColor: androidx.compose.ui.graphics.Color, // Schriftfarbe
    bodyColor: androidx.compose.ui.graphics.Color, // Hintergrundfarbe
    onClick: () -> Unit // Klickaktion
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.background(bodyColor), // Hintergrundfarbe des Buttons
    ) {
        Image(
            painter = painterResource(id = iconRes), // Lädt das Symbol
            contentDescription = label, // Beschreibung für Barrierefreiheit
            contentScale = ContentScale.Crop, // Skaliert das Bild
            modifier = Modifier.size(64.dp), // Größe des Symbols
            colorFilter = tint(fontColor) // Farbe des Symbols
        )
    }
    Text(
        text = label, // Beschriftung des Buttons
        color = fontColor, // Dynamische Schriftfarbe
        modifier = Modifier.clickable(onClick = onClick), // Klickbarer Text
    )
}




// Vorschau für die UI in der Entwicklungsumgebung
@Preview(showBackground = true)
@Composable
fun AddMicrocontrollerPreview() {
    MyEnvironmentalsTheme {
        ConnectionTypeSelection(
            LocalContext.current,
            AddMicrocontrollerViewModel(
                StandardSettingsReader(LocalContext.current)
            )
        )
    }
}
