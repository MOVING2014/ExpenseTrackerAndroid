package com.example.expensetracker.domain.usecase

import com.example.expensetracker.domain.model.DailyTransactions
import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.repository.ExpenseRepository
import com.example.expensetracker.screens.toLocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.YearMonth
import java.util.Calendar
import javax.inject.Inject

class GetGroupedMonthlyExpensesUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    fun invoke(selectedMonth: Flow<YearMonth>, expenses: Flow<List<Expense>>): Flow<List<DailyTransactions>> {
        return combine(selectedMonth, expenses) { yearMonth, expenseList ->
            expenseList
                .filter { expense ->
                    val localDate = expense.date.toLocalDate()
                    YearMonth.from(localDate) == yearMonth
                }
                .groupBy {
                    // 将日期归一化，确保不受时区和时间的影响
                    val calendar = Calendar.getInstance()
                    calendar.time = it.date
                    calendar.set(Calendar.HOUR_OF_DAY, 0)
                    calendar.set(Calendar.MINUTE, 0)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)
                    calendar.time
                }
                .map { (date, dailyTransactions) ->
                    // 将交易按收入和支出分开
                    val (incomeTransactions, expenseTransactions) = dailyTransactions.partition {
                        it.category.type == "income"
                    }

                    // 返回 DailyTransactions 对象
                    DailyTransactions(
                        date = date,
                        transactions = dailyTransactions,
                        totalIncome = incomeTransactions.sumOf { it.amount },
                        totalExpense = expenseTransactions.sumOf { it.amount }
                    )
                }
                .sortedByDescending { it.date } // 按日期降序排序
        }
    }

}