package com.example.currency

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.currency.ui.screens.InfoScreen
import com.example.currency.ui.screens.MainScreen
import com.example.currency.viewmodel.CurrencyViewModel

@Composable
fun CurrencyConverterNavigation(viewModel: CurrencyViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(
                viewModel = viewModel,
                onNavigateToInfo = { navController.navigate("info") }
            )
        }
        composable("info") {
            InfoScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}
