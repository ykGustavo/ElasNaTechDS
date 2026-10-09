package com.elasnatech.app

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

/** As 4 abas do app. Para criar uma nova aba, adicione um item aqui e um composable no NavHost. */
sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    data object Home : Screen("home", "Home", Icons.Filled.Home)
    data object News : Screen("news", "Notícias", Icons.AutoMirrored.Filled.List)
    data object Support : Screen("support", "Apoio", Icons.Filled.Favorite)
    data object Challenges : Screen("challenges", "Desafios", Icons.Filled.Star)
}

private val bottomItems = listOf(Screen.Home, Screen.News, Screen.Support, Screen.Challenges)

/** Navega para uma aba sem empilhar telas repetidas. */
fun NavHostController.navigateToTab(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun ElasNaTechApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    GradientBackground {
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                    bottomItems.forEach { screen ->
                        NavigationBarItem(
                            selected = currentRoute == screen.route,
                            onClick = { navController.navigateToTab(screen.route) },
                            icon = { Icon(screen.icon, contentDescription = screen.label) },
                            label = { Text(screen.label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding),
                enterTransition = { fadeIn(tween(300)) },
                exitTransition = { fadeOut(tween(300)) }
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(onGoToChallenges = { navController.navigateToTab(Screen.Challenges.route) })
                }
                composable(Screen.News.route) { NewsScreen() }
                composable(Screen.Support.route) { SupportScreen() }
                composable(Screen.Challenges.route) { QuizScreen() }
            }
        }
    }
}
