package com.eriknivar.erninventario

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eriknivar.erninventario.inventoryapp.view.editcounts.EditCountsFragment
import com.eriknivar.erninventario.inventoryapp.view.inventoryentry.InventoryEntryFragment
import com.eriknivar.erninventario.inventoryapp.view.inventoryreport.InventoryReportsFragment
import com.eriknivar.erninventario.inventoryapp.view.login.LoginScreen
import com.eriknivar.erninventario.inventoryapp.view.masterdata.MasterDataFragment
import com.eriknivar.erninventario.inventoryapp.view.storagetype.SelectStorage


@Composable
fun NavHostController() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        composable(route = "login") {LoginScreen(navController) }
        composable(route = "storagetype") {SelectStorage(navController) }
        composable(route = "inventoryentry") {InventoryEntryFragment(navController) }
        composable(route = "inventoryreports") {InventoryReportsFragment(navController) }
        composable(route = "editscounts") {EditCountsFragment(navController)}
        composable(route = "masterdata") {MasterDataFragment(navController)}



    }
}


