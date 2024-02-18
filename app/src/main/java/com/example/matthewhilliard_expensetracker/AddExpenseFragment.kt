package com.example.matthewhilliard_expensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class AddExpenseFragment : Fragment() {
    private lateinit var expenseRepository: ExpenseRepository

    private lateinit var setExpenseAmount: EditText
    private lateinit var setExpenseCategory: Spinner
    private lateinit var addExpenseButton: Button
    private lateinit var backButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_expense, container, false)
        expenseRepository = ExpenseRepository(requireContext())

        backButton = view.findViewById(R.id.backButton)
        setExpenseAmount = view.findViewById(R.id.setExpenseAmountInput)
        setExpenseCategory = view.findViewById(R.id.setExpenseCategory)
        addExpenseButton = view.findViewById(R.id.addExpenseButton)

        addExpenseButton.setOnClickListener(){
            if(setExpenseAmount.text.isNotEmpty() && setExpenseAmount.text.toString() != "."){
                val currExpense = Expense(
                    id = UUID.randomUUID(),
                    date = Date(),
                    amount = setExpenseAmount.text.toString().toDouble(),
                    category = setExpenseCategory.selectedItem.toString()
                )

                lifecycleScope.launch {
                    expenseRepository.insertExpense(currExpense)
                }

                val listExpenseFragment = ExpenseListFragment()
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, listExpenseFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            else{
                Toast.makeText(requireContext(), "Invalid amount", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener(){
            val listExpenseFragment = ExpenseListFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, listExpenseFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }
}