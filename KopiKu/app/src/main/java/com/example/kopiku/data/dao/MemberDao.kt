package com.example.kopiku.data.dao

import androidx.room.*
import com.example.kopiku.data.entity.Member
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {
    @Query("SELECT * FROM members ORDER BY name ASC")
    fun getAllMembers(): Flow<List<Member>>

    @Query("SELECT * FROM members WHERE id = :id")
    fun getMemberById(id: Int): Flow<Member?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(member: Member): Long

    @Update
    suspend fun updateMember(member: Member)

    @Delete
    suspend fun deleteMember(member: Member)

    @Query("UPDATE members SET points = points + :addedPoints WHERE id = :memberId")
    suspend fun addPoints(memberId: Int, addedPoints: Int)

    @Query("UPDATE members SET points = points - :deductedPoints WHERE id = :memberId")
    suspend fun deductPoints(memberId: Int, deductedPoints: Int)
    
    @Query("SELECT COUNT(*) FROM members")
    fun getMemberCount(): Flow<Int>
}
