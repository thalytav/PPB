package com.example.tugas14_newsapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tugas14_newsapp.data.model.Article
import com.example.tugas14_newsapp.ui.components.FeaturedNewsCard
import com.example.tugas14_newsapp.ui.components.NewsItemRow
import com.example.tugas14_newsapp.viewmodel.NewsUiState
import com.example.tugas14_newsapp.viewmodel.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: NewsViewModel,
    onDetailClick: (Article) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Home", "Search", "Saved")

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            "News App",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                )
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = Color.White
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(title, color = Color.White) }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> HomeContent(viewModel, onDetailClick)
                1 -> SearchContent(viewModel, onDetailClick)
                2 -> SavedContent(viewModel, onDetailClick)
            }
        }
    }
}

@Composable
fun HomeContent(viewModel: NewsViewModel, onDetailClick: (Article) -> Unit) {
    val state by viewModel.uiState.collectAsState()

    when (state) {
        is NewsUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
        is NewsUiState.Success -> {
            val articles = (state as NewsUiState.Success).articles
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                if (articles.isNotEmpty()) {
                    item {
                        FeaturedNewsCard(article = articles[0]) {
                            onDetailClick(articles[0])
                        }
                        Text(
                            text = "Breaking News",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                    itemsIndexed(articles.drop(1)) { _, article ->
                        NewsItemRow(article = article) {
                            onDetailClick(article)
                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            thickness = 0.5.dp,
                            color = Color.LightGray.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
        is NewsUiState.Error -> {
            val error = state as NewsUiState.Error
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(error.message, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.loadNews() }) {
                    Text("Retry")
                }
            }
        }
    }
}

@Composable
fun SearchContent(viewModel: NewsViewModel, onDetailClick: (Article) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            shape = MaterialTheme.shapes.medium,
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Search Results",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(10.dp))

        when (uiState) {
            is NewsUiState.Success -> {
                val allArticles = (uiState as NewsUiState.Success).articles
                val filteredArticles = allArticles.filter {
                    it.title.contains(searchQuery, ignoreCase = true) ||
                            (it.description?.contains(searchQuery, ignoreCase = true) ?: false)
                }

                if (filteredArticles.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No articles found matching \"$searchQuery\"")
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        itemsIndexed(filteredArticles) { _, article ->
                            NewsItemRow(article = article) {
                                onDetailClick(article)
                            }
                            HorizontalDivider(
                                thickness = 0.5.dp,
                                color = Color.LightGray.copy(alpha = 0.5f)
                            )
                        }
                    }
                }
            }
            is NewsUiState.Loading -> {
                CircularProgressIndicator()
            }
            is NewsUiState.Error -> {
                Text("Error loading data to search from")
            }
        }
    }
}

@Composable
fun SavedContent(viewModel: NewsViewModel, onDetailClick: (Article) -> Unit) {
    val savedArticles by viewModel.savedArticles.collectAsState()

    if (savedArticles.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No saved articles yet", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                text = "Saved Articles",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyColumn {
                itemsIndexed(savedArticles) { _, article ->
                    NewsItemRow(article = article) {
                        onDetailClick(article)
                    }
                    HorizontalDivider(
                        thickness = 0.5.dp,
                        color = Color.LightGray.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}
