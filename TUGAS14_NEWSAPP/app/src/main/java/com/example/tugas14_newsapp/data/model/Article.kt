package com.example.tugas14_newsapp.data.model

data class Article(
    val title: String,
    val description: String?,
    val content: String?,
    val author: String?,
    val urlToImage: String?,
    val publishedAt: String
)
