package com.example.kopiku

import android.app.Application
import com.example.kopiku.data.AppDatabase
import com.example.kopiku.data.repository.KopiKuRepository

class KopiKuApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { KopiKuRepository(database.memberDao(), database.transactionDao()) }
}
