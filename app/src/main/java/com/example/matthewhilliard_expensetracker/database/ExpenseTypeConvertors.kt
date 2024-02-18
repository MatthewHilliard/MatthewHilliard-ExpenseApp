package com.example.matthewhilliard_expensetracker.database

import androidx.room.TypeConverter
import java.util.Date

class ExpenseTypeConvertors {
    @TypeConverter
    fun fromDate(date: Date): Long{
        return date.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch)
    }
}