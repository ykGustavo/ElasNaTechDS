package com.elasnatech.app

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.elasnatech.app.ui.profile.CreateProfileScreen
import com.elasnatech.app.ui.profile.ProfileScreen
import com.elasnatech.app.ui.profile.rememberHasProfile
import com.elasnatech.app.viewmodel.ProfileViewModel

/** As 5 abas do app. Para criar uma nova aba, adicione um item aqui e um composable no NavHost. */
sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    data object Home : Screen("home", "Home", Icons.Filled.Home)
    data object News : Screen("news", "Notícias", Icons.AutoMirrored.Filled.List)
    data object Support : Screen("support", "Apoio", Icons.Filled.Favorite)
    data object Challenges : Screen("challenges", "Desafios", Icons.Filled.Star)
    data object Profile : Screen("profile", "Perfil", Icons.Filled.Person)
}

/** Tela de criação de perfil (onboarding): não é aba, aparece sem a barra de navegação. */
const val CREATE_PROFILE_ROUTE = "create_profile"

private val bottomItems = listOf(
    Screen.Home, Screen.News, Screen.Support, Screen.Challenges, Screen.Profile
)

/** Navega para uma aba sem empilhar telas repetidas. */
fun NavHostController.navigateToTab(route: String) {
    navigate(route) {
        // Volta sempre até a Home. Não dá para usar findStartDestination() porque,
        // na primeira vez, o destino inicial é a tela de criar perfil.
        popUpTo(Screen.Home.route) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun ElasNaTechApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val profileViewModel: ProfileViewModel = viewModel()
    // null = ainda lendo o banco; false = sem perfil (abre onboarding); true = abre a Home
    val hasProfile = rememberHasProfile()
    val showBottomBar = bottomItems.any { it.route == currentRoute }

    GradientBackground {
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                if (showBottomBar) {
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
            }
        ) { innerPadding ->
            if (hasProfile == null) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                NavHost(
                    navController = navController,
                    startDestination = if (hasProfile) Screen.Home.route else CREATE_PROFILE_ROUTE,
                    modifier = Modifier.padding(innerPadding),
                    enterTransition = { fadeIn(tween(300)) },
                    exitTransition = { fadeOut(tween(300)) }
                ) {
                    composable(CREATE_PROFILE_ROUTE) {
                        CreateProfileScreen(
                            onCreate = { name, avatarId ->
                                profileViewModel.atualizarPerfil(name, avatarId)
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(CREATE_PROFILE_ROUTE) { inclusive = true }
                                }
                            }
                        )
                    }
                    composable(Screen.Home.route) {
                        HomeScreen(onGoToChallenges = { navController.navigateToTab(Screen.Challenges.route) })
                    }
                    composable(Screen.News.route) { NewsScreen() }
                    composable(Screen.Support.route) { SupportScreen() }
                    composable(Screen.Challenges.route) { QuizScreen() }
                    composable(Screen.Profile.route) {
                        val perfil by profileViewModel.perfil.collectAsState()
                        val p = perfil
                        if (p == null) {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        } else {
                            ProfileScreen(
                                profile = p,
                                nivel = profileViewModel.calcularNivelAtual(p.xp)
                            )
                        }
                    }
                }
            }
        }
    }
}
