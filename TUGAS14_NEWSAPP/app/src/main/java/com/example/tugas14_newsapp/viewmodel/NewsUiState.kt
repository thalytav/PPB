package com.example.tugas14_newsapp.viewmodel

import com.example.tugas14_newsapp.data.model.Article

sealed class NewsUiState {
    object Loading : NewsUiState()
    data class Success(val articles: List<Article>) : NewsUiState()
    data class Error(val message: String) : NewsUiState()
}
