package com.example.tugas13_registrasisiswa.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tugas13_registrasisiswa.data.Siswa
import com.example.tugas13_registrasisiswa.viewmodel.StudentViewModel

@Composable
fun MainScreen(
    viewModel: StudentViewModel
) {
    var nama by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var studentToDelete by remember { mutableStateOf<Siswa?>(null) }
    var editingStudent by remember { mutableStateOf<Siswa?>(null) }
    val siswaList by viewModel.siswaList.collectAsState()

    if (showDeleteDialog && studentToDelete != null) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                studentToDelete = null
            },
            title = { Text(text = "Konfirmasi Hapus") },
            text = { Text(text = "Apakah Anda yakin ingin menghapus data siswa ${studentToDelete?.nama}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        studentToDelete?.let { viewModel.hapusSiswa(it) }
                        showDeleteDialog = false
                        studentToDelete = null
                    }
                ) {
                    Text("Hapus", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        studentToDelete = null
                    }
                ) {
                    Text("Batal")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Registrasi Siswa",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Kelola data siswa",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        FormInput(
            nama = nama,
            email = email,
            isEditMode = editingStudent != null,
            onNamaChange = { nama = it },
            onEmailChange = { email = it },
            onActionClick = {
                if (nama.isBlank()) return@FormInput
                if (email.isBlank()) return@FormInput
                if (!email.contains("@")) return@FormInput

                if (editingStudent != null) {
                    viewModel.editSiswa(editingStudent!!.copy(nama = nama, email = email))
                    editingStudent = null
                } else {
                    viewModel.tambahSiswa(nama, email)
                }
                
                nama = ""
                email = ""
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (siswaList.isEmpty()) {
            Text(text = "Belum ada data siswa")
        }

        LazyColumn {
            items(siswaList) { siswa ->
                StudentItem(
                    siswa = siswa,
                    onDelete = {
                        studentToDelete = siswa
                        showDeleteDialog = true
                    },
                    onEdit = {
                        editingStudent = siswa
                        nama = siswa.nama
                        email = siswa.email
                    }
                )
            }
        }
    }
}