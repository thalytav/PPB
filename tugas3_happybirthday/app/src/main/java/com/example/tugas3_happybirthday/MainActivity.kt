package com.example.tugas3_happybirthday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tugas3_happybirthday.ui.theme.Tugas3_happybirthdayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tugas3_happybirthdayTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        message = "HAPPY BIRTHDAY SAM!",
                        from = "From Emma...",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(message: String, from: String, modifier: Modifier = Modifier) {
    // Column dipakai biar teksnya nyusun rapi dari atas ke bawah
    Column(modifier = modifier) {
        Text(
            text = message
        )
        Text(
            text = from
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Tugas3_happybirthdayTheme {
        Greeting(
            message = "HAPPY BIRTHDAY SAM!",
            from = "From Emma..."
        )
    }
}