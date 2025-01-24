package com.eriknivar.erninventario.inventoryapp.view.inventoryentry

import android.app.DatePickerDialog
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.eriknivar.erninventario.R
import com.eriknivar.erninventario.inventoryapp.view.EditCounts
import com.eriknivar.erninventario.inventoryapp.view.Exit
import com.eriknivar.erninventario.inventoryapp.view.InventoryEntry
import com.eriknivar.erninventario.inventoryapp.view.InventoryReports
import com.eriknivar.erninventario.inventoryapp.view.MasterData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.coroutines.launch
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryEntryFragment(navController: NavHostController) {

    val activity = getActivity()

    val drawerState =
        rememberDrawerState(initialValue = DrawerValue.Closed) // el estado del drawer se crea aqui, ahi se puede cambiar el estado, digase el Menu Cerrado
    val scope =
        rememberCoroutineScope() // El scope es para abrir y cerrar el drawer, se ejecuta un segundo hilo de ejecucion

    val locationFocusRequester = FocusRequester()
    val codMaterialFocusRequester = FocusRequester()
    val lotFocusRequester = FocusRequester()
    val expirationDateFocusRequester = FocusRequester()
    val quantityFocusRequester = FocusRequester()

    val qrCodeContentLocation = remember { mutableStateOf("") } //esto es para el scanner de QRCode
    val qrCodeContentSku = remember { mutableStateOf("") } //esto es para el scanner de QRCode
    val qrCodeContentLot = remember { mutableStateOf("") } //esto es para el scanner de QRCode
    val qrCodeContentQuantity = remember { mutableStateOf("") } //esto es para el scanner de QRCode

    var location by remember { mutableStateOf("") }
    var sku by remember { mutableStateOf("") }
    var lot by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }


    val qrScanLauncherLocation =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            val intentResult = IntentIntegrator.parseActivityResult(result.resultCode, data)
            if (intentResult != null) {
                if (intentResult.contents != null) {
                    qrCodeContentLocation.value = intentResult.contents
                } else {
                    qrCodeContentLocation.value = "Codigo No Encontrado"
                }
            }
        }

    val qrScanLauncherSku =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            val intentResult = IntentIntegrator.parseActivityResult(result.resultCode, data)
            if (intentResult != null) {
                if (intentResult.contents != null) {
                    qrCodeContentSku.value = intentResult.contents
                } else {
                    qrCodeContentSku.value = "Codigo No Encontrado"
                }
            }
        }

    val qrScanLauncherLot =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            val intentResult = IntentIntegrator.parseActivityResult(result.resultCode, data)
            if (intentResult != null) {
                if (intentResult.contents != null) {
                    qrCodeContentLot.value = intentResult.contents
                } else {
                    qrCodeContentLot.value = "Codigo No Encontrado"
                }
            }
        }

    val qrScanLauncherQuantity =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            val intentResult = IntentIntegrator.parseActivityResult(result.resultCode, data)
            if (intentResult != null) {
                if (intentResult.contents != null) {
                    qrCodeContentQuantity.value = intentResult.contents
                } else {
                    qrCodeContentQuantity.value = "Codigo No Encontrado"
                }
            }
        }

    val qrCodeScannerLocation = remember { QRCodeScanner(qrScanLauncherLocation) }
    val qrCodeScannerSku = remember { QRCodeScanner(qrScanLauncherSku) }
    val qrCodeScannerLot = remember { QRCodeScanner(qrScanLauncherLot) }
    val qrCodeScannerQuantity = remember { QRCodeScanner(qrScanLauncherQuantity) }


    LaunchedEffect(qrCodeContentLocation.value) {
        location = qrCodeContentLocation.value.uppercase()
    }


    LaunchedEffect(qrCodeContentSku.value) {
        sku = qrCodeContentSku.value.uppercase()
    }


    LaunchedEffect(qrCodeContentLot.value) {
        lot = qrCodeContentLot.value.uppercase()
    }


    LaunchedEffect(qrCodeContentQuantity.value) {
        quantity = qrCodeContentQuantity.value.uppercase()
    }

    var inventoryItems by remember { mutableStateOf(listOf<String>()) }

    val expirationDate = remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    // Estado para la edición
    var isEditing by remember { mutableStateOf(false) }
    var currentItem by remember { mutableStateOf("") }
    var editedItem by remember { mutableStateOf("") }

    var showError by remember { mutableStateOf(false) }// Para validar los campos vacios

    var showDialog by remember { mutableStateOf(false) }
    var itemToDelete by remember { mutableStateOf<String?>(null) }



    BackHandler(true) {
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
                    painter = painterResource(id = R.drawable.erik),
                    contentDescription = ""
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


                TextButton(modifier = Modifier.padding(), onClick = {
                    navController.navigate("storagetype")
                    scope.launch { drawerState.close() }
                }) {
                    InventoryEntry(navController)
                }


                TextButton(modifier = Modifier.padding(), onClick = {
                    navController.navigate("inventoryreports")
                    scope.launch { drawerState.close() }
                }) {
                    InventoryReports(navController)
                }

                TextButton(modifier = Modifier.padding(), onClick = {
                    navController.navigate("editscounts")
                    scope.launch { drawerState.close() }
                }) {
                    EditCounts(navController)
                }

                TextButton(modifier = Modifier.padding(), onClick = {
                    navController.navigate("masterdata")
                    scope.launch { drawerState.close() }
                }) {
                    MasterData(navController)

                }


                TextButton(onClick = { navController.navigate("login") })

                {
                    Exit(navController)
                }
            }

        }

    })// El ModalNavigationDrawer tiene que contener el Scaffold


    {

        val customColorBackGround = Color(0xFF527782)

        Scaffold(topBar = {
            TopAppBar(navigationIcon = {
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
                    text = "Aqui va la Descripcion del Item",
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
                        modifier = Modifier.fillMaxWidth()

                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .padding(8.dp)
                                .width(250.dp)
                                .fillMaxWidth()
                                .focusRequester(locationFocusRequester),

                            singleLine = true,

                            label = { Text(text = "Ubicación") },
                            value = location,
                            onValueChange = { newValue ->
                                location = newValue.uppercase()
                                qrCodeContentLocation.value = newValue.uppercase()


                            },
                            isError = showError && location.isEmpty()
                        )


                        IconButton(modifier = Modifier
                            .padding(16.dp)
                            .size(50.dp),
                            onClick = { activity?.let { qrCodeScannerLocation.startQRCodeScanner(it) } }) {


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

                Box(
                    modifier = Modifier
                        .padding()
                        .size(500.dp, 70.dp)


                ) {


                    Row(
                        modifier = Modifier.fillMaxWidth()

                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .padding(8.dp)
                                .width(250.dp)
                                .fillMaxWidth()
                                .focusRequester(codMaterialFocusRequester),

                            singleLine = true,

                            label = { Text(text = "Cod. Material") },
                            value = sku,
                            onValueChange = { newValue ->
                                sku = newValue.uppercase()
                                qrCodeContentSku.value = newValue.uppercase()


                            },
                            isError = showError && location.isEmpty()
                        )


                        IconButton(modifier = Modifier
                            .padding(16.dp)
                            .size(50.dp),
                            onClick = { activity?.let { qrCodeScannerSku.startQRCodeScanner(it) } }) {
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

                Box(
                    modifier = Modifier
                        .padding()
                        .size(500.dp, 70.dp)


                ) {


                    Row(
                        modifier = Modifier.fillMaxWidth()

                    ) {
                        OutlinedTextField(modifier = Modifier
                            .padding(8.dp)
                            .width(250.dp)
                            .fillMaxWidth()
                            .focusRequester(lotFocusRequester),

                            singleLine = true,

                            label = { Text(text = "Lote") },
                            value = lot,
                            onValueChange = { newValue ->
                                lot = newValue.uppercase()
                                qrCodeContentLot.value = newValue.uppercase()

                            })


                        IconButton(modifier = Modifier
                            .padding(16.dp)
                            .size(50.dp),
                            onClick = { activity?.let { qrCodeScannerLot.startQRCodeScanner(it) } }) {
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

                Box(
                    modifier = Modifier
                        .padding()
                        .size(500.dp, 70.dp)


                ) {


                    val calendar = Calendar.getInstance()
                    val datePickerDialog = DatePickerDialog(
                        activity ?: LocalContext.current,
                        { _, year, month, dayOfMonth ->
                            expirationDate.value = "$dayOfMonth/${month + 1}/$year"
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth()

                    ) {
                        OutlinedTextField(modifier = Modifier
                            .padding(8.dp)
                            .width(250.dp)
                            .fillMaxWidth()
                            .focusRequester(expirationDateFocusRequester),

                            singleLine = true,

                            label = { Text(text = "Fecha de Caducidad") },
                            value = expirationDate.value,

                            onValueChange = {
                                expirationDate.value = it

                            },

                            trailingIcon = {
                                IconButton(onClick = { datePickerDialog.show() }) {
                                    Icon(
                                        imageVector = Icons.Default.CalendarMonth,
                                        contentDescription = "Select Date"
                                    )
                                }
                            })


                    }


                }

                Box(
                    modifier = Modifier
                        .padding()
                        .size(500.dp, 70.dp)
                    //.background(color = Color.Yellow)


                ) {


                    Row(
                        modifier = Modifier.fillMaxWidth()

                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .padding(8.dp)
                                .width(250.dp)
                                .fillMaxWidth()
                                .focusRequester(quantityFocusRequester),
                            singleLine = true,
                            label = { Text(text = "Cantidad") },
                            value = quantity,
                            onValueChange = { newValue ->
                                if (newValue.all { it.isDigit() }) {
                                    quantity = newValue.uppercase()
                                    qrCodeContentQuantity.value = newValue.uppercase()

                                    showError =
                                        false // Ocultar el mensaje de error si la entrada es válida
                                } else {
                                    showError = true
                                    errorMessage = "Solo se permite entrada numérica"
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = showError
                        )

                        if (showError) {
                            Text(
                                text = errorMessage,
                                color = Color.Red,
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                       /* IconButton(
                            modifier = Modifier
                                .padding(16.dp)
                                .size(50.dp),
                            onClick = { activity?.let { qrCodeScannerQuantity.startQRCodeScanner(it) } }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.QrCodeScanner,
                                contentDescription = "Icon",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .size(50.dp)
                                    .align(Alignment.Top)
                            )
                        }*/

                    }


                }




                fun sendDataToFirestore(
                    location: String,
                    sku: String,
                    lot: String,
                    expirationDate: String,
                    quantity: String
                ) {
                    val db = FirebaseFirestore.getInstance()

                    // Crear un nuevo documento con los datos ingresados
                    val item = hashMapOf(
                        "ubicacion" to location,
                        "cod_material" to sku,
                        "lote" to lot,
                        "fecha_caducidad" to expirationDate,
                        "cantidad" to quantity,
                        "timestamp" to System.currentTimeMillis()
                    )

                    db.collection("inventario")  // Nombre de la colección en Firestore
                        .add(item)
                        .addOnSuccessListener { documentReference ->
                            println("Datos enviados con éxito: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            println("Error al enviar datos: ${e.message}")
                        }
                }


                fun saveToFirestore(
                    location: String,
                    sku: String,
                    lot: String,
                    expirationDate: String,
                    quantity: String
                ) {
                    val db = FirebaseFirestore.getInstance()
                    val item = hashMapOf(
                        "ubicacion" to location,
                        "codigo_material" to sku,
                        "lote" to lot,
                        "fecha_caducidad" to expirationDate,
                        "cantidad" to quantity
                    )

                    db.collection("inventario")
                        .add(item)
                        .addOnSuccessListener {
                            Log.d("Firestore", "Datos guardados correctamente")
                        }
                        .addOnFailureListener { e ->
                            Log.e("Firestore", "Error al guardar datos", e)
                        }
                }


                //var errorMessage by remember { mutableStateOf("") }

                Button(
                    onClick = {
                        if (location.isEmpty() || sku.isEmpty() || quantity.isEmpty()) {
                            // Mostrar notificador al usuario de que hay campos vacíos
                            errorMessage = "Campos Obligatorios Vacios"
                            showError = true
                        } else {
                            val newItem =
                                "Ubicación : $location\nCod. Material: $sku\nLote: $lot\nFecha de Caducidad: ${expirationDate.value}\nCantidad: $quantity"

                            inventoryItems = inventoryItems + newItem

                            sendDataToFirestore(location, sku, lot, expirationDate.value, quantity)

                            //addCard(newItem)

                            //location = ""
                            sku = ""
                            lot = ""
                            quantity = ""
                            expirationDate.value = ""

                            codMaterialFocusRequester.requestFocus()
                        }
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Enviar Datos")
                }

                if (showError) {

                    AlertDialog(
                        onDismissRequest = { showError = false },
                        title = { Text(text = "Error") },
                        text = { Text(text = errorMessage) },
                        confirmButton = {
                            Button(onClick = { showError = false }) {
                                Text("OK")
                            }
                        }
                    )
                }

                @Composable
                fun ConfirmDeleteDialog(
                    item: String,
                    onConfirm: () -> Unit,
                    onDismiss: () -> Unit
                ) {
                    AlertDialog(
                        onDismissRequest = onDismiss,
                        title = { Text("Confirmar eliminación") },
                        text = { Text("¿Estás seguro de que deseas eliminar este registro?") },
                        confirmButton = {
                            Button(onClick = onConfirm) {
                                Text("Eliminar")
                            }
                        },
                        dismissButton = {
                            Button(onClick = onDismiss) {
                                Text("Cancelar")
                            }
                        }
                    )
                }


                // Mostrar los datos en Cards

                @Composable
                fun StyledText(
                    item: String,
                    titleColor: Color
                ) {
                    item.split("\n").forEach { part ->
                        val parts = part.split(": ")
                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = titleColor,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append(parts[0] + ": ")
                                }
                                if (parts.size > 1) {
                                    withStyle(style = SpanStyle(color = Color.Black)) {
                                        append(parts[1])
                                    }
                                }
                            },
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    inventoryItems.forEach { item ->
                        Card(
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth(),
                            elevation = CardDefaults.cardElevation()
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                item.split(", ").forEach { part ->
                                    StyledText(item = part, titleColor = Color.Blue)
                                }

                                //Spacer(modifier = Modifier.height(4.dp)) // Añadir un espaciador entre el texto y el botón

                                Row(
                                    horizontalArrangement = Arrangement.Absolute.Right,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    IconButton(onClick = {
                                        showDialog = true
                                        itemToDelete = item
                                        // inventoryItems = inventoryItems.filter { it != item }
                                    }) {
                                        Icon(
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = "Delete",
                                            tint = Color.Red
                                        )
                                    }

                                    IconButton(onClick = {
                                        currentItem = item
                                        editedItem = item
                                        isEditing = true
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Edit",
                                            tint = Color.Blue
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                if (showDialog && itemToDelete != null) {
                    ConfirmDeleteDialog(
                        item = itemToDelete!!,
                        onConfirm = {
                            inventoryItems = inventoryItems.filter { it != itemToDelete }
                            showDialog = false
                            itemToDelete = null
                        },
                        onDismiss = {
                            showDialog = false
                            itemToDelete = null
                        }
                    )
                }

                if (isEditing) {
                    val parts = currentItem.split("\n").map { it.split(": ") }
                    val editableContent = remember {
                        mutableStateListOf(*parts.map { if (it.size > 1) it[1] else "" }
                            .toTypedArray())
                    }

                    AlertDialog(
                        onDismissRequest = { isEditing = false },
                        title = { Text(text = "Editar Datos") },
                        text = {
                            Column {
                                parts.forEachIndexed { index, part ->
                                    if (part.size > 1) {
                                        OutlinedTextField(
                                            value = editableContent[index].uppercase(),
                                            onValueChange = { newValue ->
                                                editableContent[index] = newValue
                                            },
                                            singleLine = true,
                                            label = { Text(text = part[0]) },
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .fillMaxWidth()
                                        )
                                    }
                                }
                            }
                        },
                        confirmButton = {
                            Button(onClick = {
                                val updatedItem = parts.mapIndexed { index, part ->
                                    if (part.size > 1) {
                                        "${part[0]}: ${editableContent[index]}"
                                    } else {
                                        part.joinToString(": ")
                                    }
                                }.joinToString("\n")

                                inventoryItems =
                                    inventoryItems.map { if (it == currentItem) updatedItem else it }
                                isEditing = false
                            }) {
                                Text("Guardar")
                            }
                        },
                        dismissButton = {
                            Button(onClick = { isEditing = false }) {
                                Text("Cancelar")
                            }
                        }
                    )
                }


            }
        }
    }
}

