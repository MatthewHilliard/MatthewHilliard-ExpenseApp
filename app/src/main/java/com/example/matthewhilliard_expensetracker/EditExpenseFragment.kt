package com.example.matthewhilliard_expensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.UUID

class EditExpenseFragment : Fragment() {
    private var id: String? = null

    private lateinit var expenseRepository: ExpenseRepository
    private lateinit var backButton: Button
    private lateinit var deleteButton: Button
    private lateinit var amountInput: EditText
    private lateinit var updateAmount: Button
    private lateinit var categoryInput: Spinner
    private lateinit var updateCategory: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_expense, container, false)

        backButton = view.findViewById(R.id.backButton)
        deleteButton = view.findViewById(R.id.deleteButton)
        updateAmount = view.findViewById(R.id.updateAmount)
        amountInput = view.findViewById(R.id.setExpenseAmountInput)
        updateCategory = view.findViewById(R.id.updateCategory)
        categoryInput = view.findViewById(R.id.setExpenseCategory)

        expenseRepository = ExpenseRepository(requireContext())

        backButton.setOnClickListener(){
            val listExpenseFragment = ExpenseListFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, listExpenseFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        deleteButton.setOnClickListener() {
                lifecycleScope.launch {
                    val uuid: UUID = UUID.fromString(id)
                    expenseRepository.deleteExpense(uuid)
                }
                val listExpenseFragment = ExpenseListFragment()
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, listExpenseFragment)
                transaction.addToBackStack(null)
                transaction.commit()
        }

        updateAmount.setOnClickListener(){
            if(amountInput.text.isNotEmpty() && amountInput.text.toString() != "."){
                val newAmount = amountInput.text.toString().toDouble()
                lifecycleScope.launch {
                    val uuid: UUID = UUID.fromString(id)
                    expenseRepository.updateExpenseAmount(uuid, newAmount)
                }
                view.findViewById<TextView>(R.id.expenseAmount).text = "Expense Amount: $$newAmount"
            }
            else{
                Toast.makeText(requireContext(), "Invalid amount", Toast.LENGTH_SHORT).show()
            }
        }

        updateCategory.setOnClickListener(){
            val newCategory = categoryInput.selectedItem.toString()
            lifecycleScope.launch {
                val uuid: UUID = UUID.fromString(id)
                expenseRepository.updateExpenseCategory(uuid, newCategory)
            }
            view.findViewById<TextView>(R.id.expenseCategory).text = "Expense Category: $newCategory"
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id = arguments?.getString("id")
        val amount = arguments?.getDouble("amount")
        val date = arguments?.getString("date")
        val category = arguments?.getString("category")

        view.findViewById<TextView>(R.id.expenseAmount).append(amount.toString())
        view.findViewById<TextView>(R.id.expenseDate).append(date)
        view.findViewById<TextView>(R.id.expenseCategory).append(category)
    }
}