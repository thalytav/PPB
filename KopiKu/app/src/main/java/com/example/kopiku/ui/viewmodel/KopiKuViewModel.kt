package com.example.kopiku.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kopiku.data.entity.Member
import com.example.kopiku.data.entity.Transaction
import com.example.kopiku.data.repository.KopiKuRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class KopiKuViewModel(private val repository: KopiKuRepository) : ViewModel() {

    val currentUser: StateFlow<Member?> = repository.allMembers
        .map { it.firstOrNull() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val transactions: StateFlow<List<Transaction>> = currentUser
        .filterNotNull()
        .flatMapLatest { user ->
            repository.getTransactionsForMember(user.id)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addMember(name: String, email: String, phone: String) {
        viewModelScope.launch {
            repository.insertMember(Member(name = name, email = email, phone = phone))
        }
    }

    fun updateMember(name: String, email: String, phone: String) {
        val user = currentUser.value ?: return
        viewModelScope.launch {
            repository.updateMember(user.copy(name = name, email = email, phone = phone))
        }
    }

    fun addTransaction(amount: Double) {
        val user = currentUser.value ?: return
        viewModelScope.launch {
            val points = (amount / 10000).toInt()
            val date = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(Date())
            repository.addTransaction(
                Transaction(
                    memberId = user.id,
                    amount = amount,
                    pointEarned = points,
                    date = date
                )
            )
        }
    }

    fun redeemReward(rewardName: String, pointsCost: Int) {
        val user = currentUser.value ?: return
        viewModelScope.launch {
            val date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
            repository.redeemReward(user.id, pointsCost, rewardName, "Redeem: $rewardName ($date)")
        }
    }

    fun logout() {
        viewModelScope.launch {
            val user = currentUser.value
            if (user != null) {
                repository.deleteMember(user)
            }
        }
    }
}
