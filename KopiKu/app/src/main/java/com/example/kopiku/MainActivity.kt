package com.example.kopiku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kopiku.ui.component.BottomNavigationBar
import com.example.kopiku.ui.screen.*
import com.example.kopiku.ui.theme.KopiKuTheme
import com.example.kopiku.ui.viewmodel.AppViewModelProvider
import com.example.kopiku.ui.viewmodel.KopiKuViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KopiKuTheme {
                KopiKuApp()
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object AddMember : Screen("add_member")
    object Home : Screen("home")
    object MembershipCard : Screen("membership_card")
    object TransactionHistory : Screen("transaction_history")
    object AddTransaction : Screen("add_transaction")
    object Reward : Screen("reward")
    object Profile : Screen("profile")
}

@Composable
fun KopiKuApp(viewModel: KopiKuViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val navController = rememberNavController()
    val user by viewModel.currentUser.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.MembershipCard.route,
        Screen.Reward.route,
        Screen.Profile.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(onTimeout = {
                    if (user != null) {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screen.AddMember.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                })
            }
            composable(Screen.AddMember.route) {
                AddMemberScreen(
                    viewModel = viewModel,
                    onBackClick = { /* No back on registration */ },
                    onSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.AddMember.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = viewModel,
                    onCardClick = { navController.navigate(Screen.MembershipCard.route) },
                    onTransactionClick = { navController.navigate(Screen.TransactionHistory.route) },
                    onRewardClick = { navController.navigate(Screen.Reward.route) },
                    onProfileClick = { navController.navigate(Screen.Profile.route) }
                )
            }
            composable(Screen.MembershipCard.route) {
                MembershipCardScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(Screen.TransactionHistory.route) {
                TransactionHistoryScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onAddTransactionClick = { navController.navigate(Screen.AddTransaction.route) }
                )
            }
            composable(Screen.AddTransaction.route) {
                TransactionScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onSuccess = { navController.popBackStack() }
                )
            }
            composable(Screen.Reward.route) {
                RewardScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onLogout = {
                        navController.navigate(Screen.AddMember.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
