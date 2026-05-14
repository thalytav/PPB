package com.example.tugas12_loginmvvmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugas12_loginmvvmap.data.local.database.AppDatabase
import com.example.tugas12_loginmvvmap.data.repository.UserRepository
import com.example.tugas12_loginmvvmap.ui.screen.LoginScreen
import com.example.tugas12_loginmvvmap.ui.theme.TUGAS12_LOGINMVVMAPTheme
import com.example.tugas12_loginmvvmap.viewmodel.LoginViewModel
import com.example.tugas12_loginmvvmap.viewmodel.LoginViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val database = AppDatabase.getDatabase(this)
        val repository = UserRepository(database.userDao())
        val factory = LoginViewModelFactory(repository)

        setContent {
            TUGAS12_LOGINMVVMAPTheme {
                val viewModel: LoginViewModel = viewModel(factory = factory)
                
                LaunchedEffect(Unit) {
                    viewModel.insertDummyUser()
                }
                
                LoginScreen(viewModel)
            }
        }
    }
}
