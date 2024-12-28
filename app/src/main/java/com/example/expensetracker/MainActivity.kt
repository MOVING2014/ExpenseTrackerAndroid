package com.example.expensetracker

import MainScreen
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
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
        WindowCompat.setDecorFitsSystemWindows(window, false)
//        window.setStatusBarColor(Color.TRANSPARENT)

        // 如果需要深色图标（在浅色背景上）
        @Suppress("DEPRECATION")
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
                View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR


        setContent {
            // 使用 remember 记住 SystemUiController 实例
//            val systemUiController = rememberSystemUiController()
//
//            // 使用 SideEffect 确保 UI 相关的副作用在合适的时机执行
//            SideEffect {
//                systemUiController.setStatusBarColor(
//                    color = Color.Transparent,
//                    darkIcons = true // true 为深色图标，false 为浅色图标
//                )
//            }


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

