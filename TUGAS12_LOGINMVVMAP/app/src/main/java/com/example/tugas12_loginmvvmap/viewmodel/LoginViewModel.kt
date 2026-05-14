package com.example.tugas12_loginmvvmap.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugas12_loginmvvmap.data.local.entity.User
import com.example.tugas12_loginmvvmap.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel (
    private val repository: UserRepository
) : ViewModel() {
    var loginstate by mutableStateOf("")
        private set

    fun login(username: String, password: String){
        viewModelScope.launch {
            val user = repository.login(username, password)
            loginstate =
                if (user != null){
                    "Login berhasil"
                } else {
                    "Username atau passord salah"
                }
        }
    }

    fun insertDummyUser(){
        viewModelScope.launch{
            val dummy = repository.insert(
                User(
                    username = "admin",
                    password = "12345"
                )
            )
        }
    }
}