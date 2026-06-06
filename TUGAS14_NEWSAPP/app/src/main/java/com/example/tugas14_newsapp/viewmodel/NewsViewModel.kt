package com.example.tugas14_newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugas14_newsapp.data.model.Article
import com.example.tugas14_newsapp.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val repository = NewsRepository()
    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _savedArticles = MutableStateFlow<List<Article>>(emptyList())
    val savedArticles = _savedArticles.asStateFlow()

    var selectedArticle: Article? = null

    init {
        loadNews()
    }

    fun toggleSaveArticle(article: Article) {
        val currentList = _savedArticles.value.toMutableList()
        if (currentList.contains(article)) {
            currentList.remove(article)
        } else {
            currentList.add(article)
        }
        _savedArticles.value = currentList
    }

    fun isArticleSaved(article: Article): Boolean {
        return _savedArticles.value.contains(article)
    }

    fun loadNews() {
        viewModelScope.launch {
            try {
                _uiState.value = NewsUiState.Loading
                val response = repository.getNews()
                _uiState.value = NewsUiState.Success(response.articles)
            } catch (e: Exception) {
                _uiState.value = NewsUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}
