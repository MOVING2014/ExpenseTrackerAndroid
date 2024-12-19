package com.example.expensetracker.domain.usecase

import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.repository.ExpenseRepository
import com.example.expensetracker.screens.toLocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.YearMonth
import javax.inject.Inject

class GetMonthlyExpensesUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    fun invoke(selectedMonth: Flow<YearMonth>, expenses: Flow<List<Expense>>): Flow<List<Expense>> {
        return combine(selectedMonth, expenses) { yearMonth, expenseList ->
            expenseList.filter { expense ->
                val localDate = expense.date.toLocalDate() // 转换为本地日期
                YearMonth.from(localDate) == yearMonth // 判断是否是同一个月
            }
        }
    }


}