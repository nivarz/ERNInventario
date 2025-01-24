package com.eriknivar.erninventario.inventoryapp.view.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun TextFieldsLogin(NavController : NavHostController) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 80.dp, 16.dp, 16.dp)
            .verticalScroll(
                state = rememberScrollState(),
            )

    )

    {
        var username: String by remember {
            mutableStateOf("")
        }



        OutlinedTextField(
            modifier = Modifier.padding(),


            leadingIcon = {
                Icon(Icons.Filled.Person, contentDescription = "User")
            },

            label = {
                Text(
                    text = "Usuario", color = Color.White, fontWeight = FontWeight.Thin
                )


            },

            value = username, onValueChange = { newValue ->
                username = newValue.uppercase()

            }, singleLine = true, maxLines = 1


        )


        var password by remember {
            mutableStateOf("")
        }

        var showPassword by remember {
            mutableStateOf(false)
        }


        OutlinedTextField(
            trailingIcon = {
                IconButton(onClick = {
                    showPassword = !showPassword
                }) {
                    if (showPassword) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = "Visibility of the password"
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.VisibilityOff,
                            contentDescription = "Visibility of the password"
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.Visibility, contentDescription = "Icon"
                    )
                }
            },

            modifier = Modifier.padding(),
            leadingIcon = {
                Icon(Icons.Filled.Key, contentDescription = "User")
            },
            label = {
                Text(
                    text = "Contraseña", color = Color.White, fontWeight = FontWeight.Thin
                )
            },
            value = password,
            onValueChange = { newValue ->
                password = newValue

            },
            singleLine = true,
            maxLines = 1,
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation()

        )


    }
}

