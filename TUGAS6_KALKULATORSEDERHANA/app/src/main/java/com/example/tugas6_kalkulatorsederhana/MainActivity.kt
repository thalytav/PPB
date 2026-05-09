package com.example.tugas6_kalkulatorsederhana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tugas6_kalkulatorsederhana.ui.theme.TUGAS6_KALKULATORSEDERHANATheme

// --- WARNA EARTH TONE (SUDAH DIPERBAIKI) ---
val EarthDarkBrown = Color(0xFF4B3F2F)
val EarthSand = Color(0xFFDDB892)
val EarthSage = Color(0xFFA3B18A)
val EarthCream = Color(0xFFF5EBE0)
val EarthDeepGreen = Color(0xFF3A5A40)
val EarthTerracotta = Color(0xFFBC6C25)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TUGAS6_KALKULATORSEDERHANATheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = EarthCream
                ) {
                    Scaffold(
                        containerColor = Color.Transparent,
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        CalculatorApp(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorApp(modifier: Modifier = Modifier) {
    var input1 by remember { mutableStateOf("") }
    var input2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    fun calculate(operator: String) {
        errorMessage = ""
        result = ""
        val num1 = input1.toDoubleOrNull()
        val num2 = input2.toDoubleOrNull()

        if (num1 != null && num2 != null) {
            when (operator) {
                "+" -> {
                    val res = num1 + num2
                    result = if (res % 1.0 == 0.0) res.toInt().toString() else "%.6f".format(res).trimEnd('0').trimEnd('.')
                }
                "-" -> {
                    val res = num1 - num2
                    result = if (res % 1.0 == 0.0) res.toInt().toString() else "%.6f".format(res).trimEnd('0').trimEnd('.')
                }
                "*" -> {
                    val res = num1 * num2
                    result = if (res % 1.0 == 0.0) res.toInt().toString() else "%.6f".format(res).trimEnd('0').trimEnd('.')
                }
                "/" -> {
                    if (num2 != 0.0) {
                        val res = num1 / num2
                        result = if (res % 1.0 == 0.0) res.toInt().toString() else "%.6f".format(res).trimEnd('0').trimEnd('.')
                    } else {
                        errorMessage = "Tidak bisa dibagi dengan nol!"
                    }
                }
            }
        } else {
            errorMessage = "Masukkan angka dengan benar!"
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Kalkulator Sederhana",
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black, // Huruf Judul HITAM
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            colors = CardDefaults.cardColors(containerColor = EarthSand),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Text(text = "Hasil:", color = Color.Black, fontSize = 14.sp) // Teks Hasil HITAM
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        fontSize = 18.sp,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp),
                        textAlign = TextAlign.End
                    )
                } else {
                    Text(
                        text = result.ifEmpty { "0" },
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End,
                        color = Color.Black, // Angka Hasil HITAM
                        maxLines = 1
                    )
                }
            }
        }

        OutlinedTextField(
            value = input1,
            onValueChange = { input1 = it.filter { char -> char.isDigit() || char == '.' } },
            label = { Text("Angka Pertama", color = Color.Black) }, // Label HITAM
            textStyle = TextStyle(color = Color.Black), // Angka Input HITAM
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = EarthDeepGreen,
                unfocusedBorderColor = EarthDarkBrown,
                cursorColor = Color.Black
            )
        )

        OutlinedTextField(
            value = input2,
            onValueChange = { input2 = it.filter { char -> char.isDigit() || char == '.' } },
            label = { Text("Angka Kedua", color = Color.Black) }, // Label HITAM
            textStyle = TextStyle(color = Color.Black), // Angka Input HITAM
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = EarthDeepGreen,
                unfocusedBorderColor = EarthDarkBrown,
                cursorColor = Color.Black
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OpButton("+", EarthSage) { calculate("+") }
            OpButton("-", EarthSage) { calculate("-") }
            OpButton("*", EarthSage) { calculate("*") }
            OpButton("/", EarthSage) { calculate("/") }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { if (input1.isNotEmpty()) input1 = input1.dropLast(1) },
                modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EarthTerracotta)
            ) {
                Text("Hapus", color = Color.Black) // Teks Tombol HITAM
            }
            Button(
                onClick = {
                    input1 = ""; input2 = ""; result = ""; errorMessage = ""
                },
                modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EarthDarkBrown)
            ) {
                Text("Kosongkan", color = Color.White) // Ini biarkan putih biar kelihatan di background cokelat tua
            }
        }
    }
}

@Composable
fun OpButton(symbol: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(70.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = MaterialTheme.shapes.medium,
        elevation = ButtonDefaults.buttonElevation(4.dp)
    ) {
        Text(
            text = symbol,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black // Simbol Operasi HITAM
        )
    }
}