package com.example.tugas14_newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tugas14_newsapp.ui.screens.DetailScreen
import com.example.tugas14_newsapp.ui.screens.HomeScreen
import com.example.tugas14_newsapp.viewmodel.NewsViewModel

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val viewModel: NewsViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(viewModel = viewModel) { article ->
                viewModel.selectedArticle = article
                navController.navigate("detail")
            }
        }
        composable("detail") {
            viewModel.selectedArticle?.let { article ->
                DetailScreen(
                    article = article,
                    isSaved = viewModel.isArticleSaved(article),
                    onSaveToggle = { viewModel.toggleSaveArticle(article) },
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
