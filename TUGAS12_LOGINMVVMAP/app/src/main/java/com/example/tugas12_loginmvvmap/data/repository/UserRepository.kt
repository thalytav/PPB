package com.example.tugas12_loginmvvmap.data.repository

import com.example.tugas12_loginmvvmap.data.local.dao.UserDao
import com.example.tugas12_loginmvvmap.data.local.entity.User

class UserRepository (
    private val dao: UserDao
){
    suspend fun insert(user: User){
        dao.insert(user)
    }

    suspend fun login(username: String, password: String): User? {
        return dao.login(username, password)
    }

}