package com.example.tugas13_registrasisiswa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.example.tugas13_registrasisiswa.data.AppDatabase
import com.example.tugas13_registrasisiswa.ui.MainScreen
import com.example.tugas13_registrasisiswa.ui.theme.TUGAS13_REGISTRASISISWATheme
import com.example.tugas13_registrasisiswa.viewmodel.StudentViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val dao = AppDatabase.getDatabase(applicationContext).siswaDao()

        setContent {
            TUGAS13_REGISTRASISISWATheme {
                Surface {
                    val viewModel = StudentViewModel(dao)
                    MainScreen(viewModel)
                }
            }
        }
    }
}