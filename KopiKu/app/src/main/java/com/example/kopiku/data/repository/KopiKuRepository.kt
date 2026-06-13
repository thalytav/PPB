package com.example.kopiku.data.repository

import com.example.kopiku.data.dao.MemberDao
import com.example.kopiku.data.dao.TransactionDao
import com.example.kopiku.data.entity.Member
import com.example.kopiku.data.entity.Transaction
import kotlinx.coroutines.flow.Flow

class KopiKuRepository(
    private val memberDao: MemberDao,
    private val transactionDao: TransactionDao
) {
    val allMembers: Flow<List<Member>> = memberDao.getAllMembers()
    val memberCount: Flow<Int> = memberDao.getMemberCount()

    fun getMemberById(id: Int): Flow<Member?> = memberDao.getMemberById(id)

    suspend fun insertMember(member: Member): Long = memberDao.insertMember(member)

    suspend fun updateMember(member: Member) = memberDao.updateMember(member)

    suspend fun deleteMember(member: Member) = memberDao.deleteMember(member)

    suspend fun addTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
        memberDao.addPoints(transaction.memberId, transaction.pointEarned)
    }

    suspend fun redeemReward(memberId: Int, pointCost: Int, rewardName: String, date: String) {
        memberDao.deductPoints(memberId, pointCost)
        transactionDao.insertTransaction(
            Transaction(
                memberId = memberId,
                amount = 0.0,
                pointEarned = -pointCost,
                date = date // Format: "Redeem: Espresso"
            )
        )
    }

    fun getTransactionsForMember(memberId: Int): Flow<List<Transaction>> =
        transactionDao.getTransactionsForMember(memberId)
}
