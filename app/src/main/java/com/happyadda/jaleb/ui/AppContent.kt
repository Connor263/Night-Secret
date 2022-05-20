package com.happyadda.jaleb.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.happyadda.jaleb.ui.game.GamePage
import com.happyadda.jaleb.ui.game.menu.MenuPage
import com.happyadda.jaleb.ui.game.score.ScorePage
import com.happyadda.jaleb.ui.web.WebPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(), containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "init",
            modifier = Modifier.padding(padding)
        ) {
            composable("init") { NigInitPage(navController) }
            composable("menu") { MenuPage(navController) }
            composable("game") { GamePage(navController) }
            composable("score") { ScorePage(navController) }

            composable(
                "web/{id}",
                arguments = listOf(navArgument("id") {
                    defaultValue = ""
                    type = NavType.StringType
                })
            ) {
                WebPage(
                    navController,
                    it.arguments?.getString("id")!!
                )
            }
        }
    }
}