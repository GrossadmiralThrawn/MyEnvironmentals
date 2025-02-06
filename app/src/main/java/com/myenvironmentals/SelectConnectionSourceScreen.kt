package com.myenvironmentals




import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.myenvironmentals.viewmodels.ConnectionViewModel




@Composable
fun SelectConnectionSourceScreen(connectionViewModel: ConnectionViewModel)
{
    Scaffold (
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(connectionViewModel.getColor('b'))
        )
        {

        }
    }
}