package com.example.matthewhilliard_expensetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseListAdapter(private val expenses: MutableList<Expense>) : RecyclerView.Adapter<ExpenseListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_expense, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return expenses.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(expenses[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val expenseCategory: TextView = itemView.findViewById(R.id.expense_category)
        private val expenseAmount: TextView = itemView.findViewById(R.id.expense_amount)
        private val expenseDate: TextView = itemView.findViewById(R.id.expense_date)

        fun bind(expense: Expense) {
            expenseCategory.text = expense.category
            expenseAmount.text = "$"+expense.amount.toString()
            expenseDate.text = expense.date.toString()
        }
    }
}