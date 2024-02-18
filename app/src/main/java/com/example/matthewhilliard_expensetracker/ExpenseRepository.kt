package com.example.matthewhilliard_expensetracker

import android.content.Context
import androidx.room.Room
import com.example.matthewhilliard_expensetracker.database.ExpenseDatabase
import kotlinx.coroutines.flow.Flow
import java.lang.IllegalStateException
import java.util.UUID

class ExpenseRepository private constructor(context: Context) {
    private val database: ExpenseDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            ExpenseDatabase::class.java,
            "expense-database.db"
        )
        .createFromAsset("expense-database.db")
        .build()

    fun getExpenses(): Flow<List<Expense>> = database.expenseDAO().getExpenses()

    suspend fun getExpense(id: UUID): Expense = database.expenseDAO().getExpense(id)

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