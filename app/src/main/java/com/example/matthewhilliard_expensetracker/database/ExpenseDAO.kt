package com.example.matthewhilliard_expensetracker.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.matthewhilliard_expensetracker.Expense
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import java.util.Date

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

    @Query("DELETE FROM expense WHERE id = :id")
    suspend fun deleteExpense(id: UUID)

    @Query("UPDATE expense SET amount = :newAmount WHERE id = :id")
    suspend fun updateExpenseAmount(id: UUID, newAmount: Double)

    @Query("UPDATE expense SET category = :newCategory WHERE id = :id")
    suspend fun updateExpenseCategory(id: UUID, newCategory: String)

    @Query("UPDATE expense SET date = :newDate WHERE id = :id")
    suspend fun updateExpenseDate(id: UUID, newDate: Date)
}