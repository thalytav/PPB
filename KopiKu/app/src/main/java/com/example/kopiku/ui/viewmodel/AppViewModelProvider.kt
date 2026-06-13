package com.example.kopiku.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kopiku.KopiKuApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            KopiKuViewModel(kopiKuApplication().repository)
        }
    }
}

fun CreationExtras.kopiKuApplication(): KopiKuApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KopiKuApplication)
