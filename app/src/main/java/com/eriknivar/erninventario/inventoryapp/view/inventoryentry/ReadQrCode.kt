package com.eriknivar.erninventario.inventoryapp.view.inventoryentry

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadQRCode(startQRCodeScanner: () -> Unit, qrCodeContent: MutableState<String>) {
    val customColorBackGround = Color(0xFF527782)

    Scaffold(topBar = {
        TopAppBar(navigationIcon = {
            IconButton(onClick = {
                // Aquí puedes manejar el botón de menú
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    tint = Color.White,
                    modifier = Modifier.size(30.dp),
                    contentDescription = "Menu"
                )
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = customColorBackGround, titleContentColor = Color.Black,
        ), title = {
            Text(text = "Captura de Inventario")
        })
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(8.dp, 8.dp, 16.dp, 16.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                text = "Captura de Datos",
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                color = Color.Blue,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

            Box(
                modifier = Modifier
                    .padding()
                    .size(500.dp, 70.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(8.dp)
                            .width(250.dp)
                            .fillMaxWidth(),
                        singleLine = true,
                        label = { Text(text = "Ubicación") },
                        value = qrCodeContent.value,
                        onValueChange = { newValue ->
                            qrCodeContent.value = newValue.uppercase()
                        }
                    )

                    IconButton(
                        modifier = Modifier
                            .padding(16.dp)
                            .size(50.dp),
                        onClick = { startQRCodeScanner() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.QrCodeScanner,
                            contentDescription = "Icon",
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(50.dp)
                                .align(Alignment.Top)
                        )
                    }
                }

            }
        }
    }
}
