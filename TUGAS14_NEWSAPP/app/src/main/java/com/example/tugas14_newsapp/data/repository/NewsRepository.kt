package com.example.tugas14_newsapp.data.repository

import com.example.tugas14_newsapp.data.api.RetrofitClient

class NewsRepository {
    suspend fun getNews() = RetrofitClient.apiService.getTopHeadlines(
        apiKey = "c801ae18242049e5a3d1de7308a55a80"
    )
}
