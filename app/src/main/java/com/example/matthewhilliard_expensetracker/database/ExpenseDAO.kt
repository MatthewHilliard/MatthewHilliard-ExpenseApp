package com.example.matthewhilliard_expensetracker.database

import androidx.room.Dao
import androidx.room.Query
import com.example.matthewhilliard_expensetracker.Expense
import java.util.UUID
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDAO {
    @Query("SELECT * FROM expense")
    fun getExpenses():Flow<List<Expense>>

    @Query("SELECT * FROM expense WHERE id=(:id)")
    suspend fun getExpense(id: UUID): Expense
}