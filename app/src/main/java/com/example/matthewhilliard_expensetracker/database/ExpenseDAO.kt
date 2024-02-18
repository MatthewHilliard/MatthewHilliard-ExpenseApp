package com.example.matthewhilliard_expensetracker.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.matthewhilliard_expensetracker.Expense
import java.util.UUID
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDAO {
    @Query("SELECT * FROM expense ORDER BY date")
    fun getExpensesAscending():Flow<List<Expense>>

    @Query("SELECT * FROM expense ORDER BY date DESC")
    fun getExpensesDescending():Flow<List<Expense>>

    @Query("SELECT * FROM expense ORDER BY category ASC")
    fun getExpensesType():Flow<List<Expense>>

    @Query("SELECT * FROM expense WHERE id=(:id)")
    suspend fun getExpense(id: UUID): Expense

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExpense(expense: Expense)
}