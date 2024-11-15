package com.example.expensetracker

import MainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.screens.AddExpenseScreen
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerTheme {
                // 直接调用 MainScreen，不再传入 ViewModel
//                HomeScreen()
//                MainScreen(onAddClick = { navController.navigate("add_expense") })
                ExpenseTrackerNavigation()
            }
        }
    }
}

@Composable
fun ExpenseTrackerNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(
                onAddClick = { navController.navigate("add_expense") }
            )
        }
        composable("add_expense") {
            AddExpenseScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

