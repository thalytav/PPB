package com.example.tugas7_halamanlogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tugas7_halamanlogin.ui.theme.TUGAS7_HALAMANLOGINTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TUGAS7_HALAMANLOGINTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen()
                }
            }
        }
    }
}

@Composable
fun LoginScreen() {
    // State buat nampung inputan user
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Sesuai gambar: masuk.jpg
        Image(
            painter = painterResource(id = R.drawable.masuk),
            contentDescription = "Login image",
            modifier = Modifier.size(200.dp)
        )

        Text(text = "SELAMAT DATANG", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Masuk ke Akun Anda")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Alamat Email") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* Aksi Login */ }) {
            Text(text = "Masuk")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Lupa Password",
            modifier = Modifier.clickable { /* Aksi Lupa Password */ }
        )

        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "Atau masuk dengan")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Sesuai gambar: fb.png
            Image(
                painter = painterResource(id = R.drawable.fb),
                contentDescription = "Facebook",
                modifier = Modifier.size(60.dp).clickable { }
            )
            // Sesuai gambar: google.png
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Google",
                modifier = Modifier.size(60.dp).clickable { }
            )
            // Sesuai gambar: x.png
            Image(
                painter = painterResource(id = R.drawable.x),
                contentDescription = "X / Twitter",
                modifier = Modifier.size(60.dp).clickable { }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    TUGAS7_HALAMANLOGINTheme {
        LoginScreen()
    }
}