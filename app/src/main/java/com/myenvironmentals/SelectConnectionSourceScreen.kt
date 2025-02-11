package com.myenvironmentals




import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import com.myenvironmentals.models.connections.ExampleConnection
import com.myenvironmentals.viewmodels.ConnectionViewModel




@Composable
fun SelectConnectionSourceScreen(connectionViewModel: ConnectionViewModel, modifier: Modifier)
{
    val topBarColor by connectionViewModel.topBarColor.collectAsState()
    val fontColor by connectionViewModel.fontColor.collectAsState()
    val bodyColor by connectionViewModel.bodyColor.collectAsState()



    Scaffold (
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(bodyColor)
            )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp), // Abstand zum oberen Rand
                contentAlignment = androidx.compose.ui.Alignment.Center // Zentriert den Inhalt horizontal
            ) {
                Text(
                    text = stringResource(R.string.relay_position),
                    color = fontColor,
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = Bold)
                )
            }


            Spacer(modifier = modifier.padding(16.dp))


            ConnectionButton(
                bodyColor = bodyColor,
                fontColor = fontColor,
                iconRes = R.drawable.baseline_add_location_alt_24,
                label = R.string.local,
                onClick = { connectionViewModel.navigateToConnectionScreen(ExampleConnection()) }
            )
        }
    }



    Spacer(modifier = Modifier.padding(16.dp))
}




// Wiederverwendbare Composable für die Verbindungstyp-Buttons
@Composable
fun ConnectionButton(
    iconRes: Int, // Ressourcen-ID des Symbols
    label: Int, // Beschriftung
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
            contentDescription = label.toString(), // Beschreibung für Barrierefreiheit
            contentScale = ContentScale.Crop, // Skaliert das Bild
            modifier = Modifier.size(64.dp), // Größe des Symbols
            colorFilter = tint(fontColor) // Farbe des Symbols
        )
    }
    Text(
        text = stringResource(id = label), // Richtige Zuordnung des Textes
        color = fontColor, // Dynamische Schriftfarbe
        modifier = Modifier.clickable(onClick = onClick), // Klickbarer Text
    )
}