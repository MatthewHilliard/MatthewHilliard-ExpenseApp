package com.example.matthewhilliard_expensetracker

import android.content.Context
import androidx.room.Room
import com.example.matthewhilliard_expensetracker.database.ExpenseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import java.lang.IllegalStateException
import java.util.Date
import java.util.UUID

class ExpenseRepository(context: Context, private val coroutineScope: CoroutineScope = GlobalScope) {
    private val database: ExpenseDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            ExpenseDatabase::class.java,
            "expense-database.db"
        )
        .createFromAsset("expense-database.db")
        .build()

    fun getExpensesDescending(): Flow<List<Expense>> = database.expenseDAO().getExpensesDescending()

    fun getExpensesAscending(): Flow<List<Expense>> = database.expenseDAO().getExpensesAscending()

    fun getExpensesType(): Flow<List<Expense>> = database.expenseDAO().getExpensesType()

    suspend fun getExpense(id: UUID): Expense = database.expenseDAO().getExpense(id)

    suspend fun insertExpense(expense: Expense) = database.expenseDAO().insertExpense(expense)

    suspend fun deleteExpense(id: UUID) = database.expenseDAO().deleteExpense(id)

    suspend fun updateExpenseAmount(id: UUID, newAmount: Double) = database.expenseDAO().updateExpenseAmount(id, newAmount)

    suspend fun updateExpenseCategory(id: UUID, newCategory: String) = database.expenseDAO().updateExpenseCategory(id, newCategory)

    suspend fun updateExpenseDate(id: UUID, newDate: Date) = database.expenseDAO().updateExpenseDate(id, newDate)

    companion object {
        private var INSTANCE: ExpenseRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ExpenseRepository(context)
            }
        }

        fun get(): ExpenseRepository {
            return INSTANCE
                ?: throw IllegalStateException("ExpenseRepository must be initialized")
        }
    }
}