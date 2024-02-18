package com.example.matthewhilliard_expensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

private const val TAG = "ExpenseListFragment"

class ExpenseListFragment : Fragment() {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: ExpenseListAdapter
    private lateinit var expenseRecyclerView: RecyclerView
    private lateinit var expenseRepository: ExpenseRepository
    private lateinit var addExpenseButton: Button
    private lateinit var radioGroup: RadioGroup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expense_list, container, false)
        expenseRecyclerView = view.findViewById(R.id.expense_recycler_view)
        layoutManager = LinearLayoutManager(requireContext())
        expenseRecyclerView.layoutManager = layoutManager

        addExpenseButton = view.findViewById(R.id.goToAddExpenseButton)
        addExpenseButton.setOnClickListener(){
            val addExpenseFragment = AddExpenseFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, addExpenseFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        expenseRepository = ExpenseRepository(requireContext())
        fetchExpensesDescending()

        radioGroup = view.findViewById(R.id.radioGroup)
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButtonSortAscending -> {
                    fetchExpensesDescending()
                }
                R.id.radioButtonSortDescending -> {
                    fetchExpensesAscending()
                }
                R.id.radioButtonFilterByType -> {
                    fetchExpensesType()
                }
            }
        }

        return view
    }

    private fun fetchExpensesDescending() {
        lifecycleScope.launch {
            expenseRepository.getExpensesDescending().collect { expenses ->
                expenseRecyclerView.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = ExpenseListAdapter(expenses.toMutableList())
                }
            }
        }
    }

    private fun fetchExpensesAscending() {
        lifecycleScope.launch {
            expenseRepository.getExpensesAscending().collect { expenses ->
                expenseRecyclerView.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = ExpenseListAdapter(expenses.toMutableList())
                }
            }
        }
    }

    private fun fetchExpensesType() {
        lifecycleScope.launch {
            expenseRepository.getExpensesType().collect { expenses ->
                expenseRecyclerView.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = ExpenseListAdapter(expenses.toMutableList())
                }
            }
        }
    }
}
