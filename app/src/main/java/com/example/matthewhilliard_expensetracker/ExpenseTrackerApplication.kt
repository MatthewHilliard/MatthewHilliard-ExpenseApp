package com.example.matthewhilliard_expensetracker

import android.app.Application

class ExpenseTrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ExpenseRepository.initialize(this)
    }
}