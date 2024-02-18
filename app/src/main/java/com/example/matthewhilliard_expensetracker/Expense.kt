package com.example.matthewhilliard_expensetracker

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity
data class Expense (
    @PrimaryKey val id: UUID,
    val date: Date,
    val amount: Double,
    val category: String
    )