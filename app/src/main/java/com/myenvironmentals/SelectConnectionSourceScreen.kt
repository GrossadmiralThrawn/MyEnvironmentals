package com.myenvironmentals




import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
fun SelectConnectionSourceScreen(connectionViewModel: ConnectionViewModel) {
    val fontColor by connectionViewModel.fontColor.collectAsState()
    val bodyColor by connectionViewModel.bodyColor.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(bodyColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Überschrift oben zentriert
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.relay_position),
                    color = fontColor,
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = Bold)
                )
            }

            Spacer(modifier = Modifier.padding(50.dp)) //Regelt abstand zwischen Elemten.

            ConnectionButton(
                bodyColor = bodyColor,
                fontColor = fontColor,
                iconRes = R.drawable.baseline_add_location_alt_24,
                label = R.string.local,
                onClick = { connectionViewModel.navigateToConnectionScreen(ExampleConnection()) }
            )


            Spacer(modifier = Modifier.padding(16.dp)) //Regelt abstand zwischen Elemten.


            ConnectionButton(
                bodyColor = bodyColor,
                fontColor = fontColor,
                iconRes = R.drawable.baseline_cloud_24,
                label = R.string.local,
                onClick = { connectionViewModel.navigateToConnectionScreen(ExampleConnection()) }
            )
        }
    }
}




// Wiederverwendbarer Composable für die Verbindungstyp-Buttons
@Composable
fun ConnectionButton(
    iconRes: Int,
    label: Int,
    fontColor: androidx.compose.ui.graphics.Color,
    bodyColor: androidx.compose.ui.graphics.Color,
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
