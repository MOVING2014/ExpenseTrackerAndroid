package com.example.expensetracker.domain.usecase

import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllExpensesUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
     operator fun invoke(): Flow<List<Expense>> {
        return expenseRepository.getAllExpenses()
    }
}