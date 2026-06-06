package com.example.tugas14_newsapp.data.model

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
