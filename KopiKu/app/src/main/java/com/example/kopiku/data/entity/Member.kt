package com.example.kopiku.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "members")
data class Member(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val email: String,
    val phone: String,
    val points: Int = 0
) {
    val memberIdFormatted: String
        get() = "MBR${id.toString().padStart(5, '0')}"

    val memberLevel: String
        get() = when {
            points >= 500 -> "Gold"
            points >= 200 -> "Silver"
            else -> "Bronze"
        }
}
