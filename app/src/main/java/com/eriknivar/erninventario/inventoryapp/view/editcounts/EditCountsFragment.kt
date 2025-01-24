package com.eriknivar.erninventario.inventoryapp.view.editcounts

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.eriknivar.erninventario.R
import com.eriknivar.erninventario.inventoryapp.view.EditCounts
import com.eriknivar.erninventario.inventoryapp.view.Exit
import com.eriknivar.erninventario.inventoryapp.view.InventoryEntry
import com.eriknivar.erninventario.inventoryapp.view.InventoryReports
import com.eriknivar.erninventario.inventoryapp.view.MasterData
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCountsFragment(navController: NavHostController) {

    val drawerState =
        rememberDrawerState(initialValue = DrawerValue.Closed) // el estado del drawer se crea aqui, ahi se puede cambiar el estado, digase el Menu Cerrado
    val scope =
        rememberCoroutineScope() // El scope es para abrir y cerrar el drawer, se ejecuta un segundo hilo de ejecucion

    BackHandler(true)
    {
        Log.i("LOG_TAG", "Clicked back")//Desabilitar el boton de atras
    }

    //BackHandler(enabled = drawerState.isOpen) { scope.launch { drawerState.close() } }


    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {

        ModalDrawerSheet {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.70f)
                    .padding()
            ) {

                Image(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .clip(CircleShape)
                        .size(100.dp)
                        .height(200.dp),
                    painter = painterResource(id = R.drawable.erik), contentDescription = ""
                )


                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "Hola, Erik",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue,
                    fontStyle = FontStyle.Normal,
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))


                TextButton(
                    modifier = Modifier.padding(),
                    onClick = {
                        navController.navigate("storagetype")
                        scope.launch { drawerState.close() }
                    })
                {
                    InventoryEntry(navController)
                }


                TextButton(
                    modifier = Modifier.padding(),
                    onClick = {
                        navController.navigate("inventoryreports")
                        scope.launch { drawerState.close() }
                    })
                {
                    InventoryReports(navController)
                }

                TextButton(
                    modifier = Modifier.padding(),
                    onClick = {
                        navController.navigate("editscounts")
                        scope.launch { drawerState.close() }
                    })
                {
                    EditCounts(navController)
                }

                TextButton(
                    modifier = Modifier.padding(),
                    onClick = {
                        navController.navigate("masterdata")
                        scope.launch { drawerState.close() }
                    })
                {
                    MasterData(navController)

                }


                TextButton(onClick = { navController.navigate("login") })

                {
                    Exit(navController)
                }
            }

        }

    })// El ModalNavigationDrawer tiene que contener el Scaffold


    //Menu(navController)

    {

        val customColorBackGround = Color(0xFF527782)

        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = {

                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }


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
                        Text(text = "Editar Conteos")
                    })
            }) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
            ) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    text = "Editar Conteos",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold

                )

            }
        }
    }
}
