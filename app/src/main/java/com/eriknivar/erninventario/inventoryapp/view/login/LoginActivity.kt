package com.eriknivar.erninventario.inventoryapp.view.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.eriknivar.erninventario.R


@Composable
fun LoginScreen(navController : NavHostController) {

    val customColorBackGroundScreenLogin = Color(0xFF527782)

    Scaffold { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(customColorBackGroundScreenLogin),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .verticalScroll(
                        state = rememberScrollState(),
                    )
            )


            {
                Image(
                    painter = painterResource(id = R.drawable.logoerni),
                    contentDescription = "Logo ERNI",
                )

                Image(
                    modifier = Modifier
                        .padding(bottom = 1.dp)
                        .fillMaxWidth()
                        .size(100.dp),
                    painter = painterResource(id = R.drawable.warehouse),
                    contentDescription = "Logo ERNI",
                )


            }

            TextFieldsLogin(navController)
            LoginButton(navController)


        }

    }
}
