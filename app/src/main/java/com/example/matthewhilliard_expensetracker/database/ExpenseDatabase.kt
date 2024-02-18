package com.example.matthewhilliard_expensetracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.matthewhilliard_expensetracker.Expense

@Database(entities = [Expense::class], version = 1)
@TypeConverters(ExpenseTypeConvertors::class)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDAO(): ExpenseDAO
}