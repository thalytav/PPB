package com.example.tugas3_happybirthday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tugas3_happybirthday.ui.theme.TUGAS3_HAPPYBIRTHDAYTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TUGAS3_HAPPYBIRTHDAYTheme {
                // Menggunakan Surface sebagai container utama sesuai instruksi PDF [cite: 287, 427]
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingText(
                        message = "Happy Birthday Sam!",
                        from = "From Emma",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

// Nama fungsi diubah menjadi GreetingText sesuai instruksi PDF
@Composable
fun GreetingText(message: String, from: String, modifier: Modifier = Modifier) {
    // Menggunakan Column untuk mengatur hierarki UI secara vertikal [cite: 404]
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = message,
            fontSize = 100.sp, // Ukuran font sesuai PDF [cite: 349, 367]
            lineHeight = 116.sp, // Tinggi baris sesuai PDF [cite: 368]
            textAlign = TextAlign.Center
        )
        Text(
            text = from,
            fontSize = 36.sp, // Ukuran font pengirim sesuai PDF [cite: 384, 418]
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.End)
        )
    }
}

// Nama pratinjau diubah menjadi BirthdayCardPreview sesuai PDF
@Preview(showBackground = true)
@Composable
fun BirthdayCardPreview() {
    TUGAS3_HAPPYBIRTHDAYTheme {
        GreetingText(
            message = "Happy Birthday Sam!",
            from = "From Emma"
        )
    }
}