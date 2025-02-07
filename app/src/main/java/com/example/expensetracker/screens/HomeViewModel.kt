package com.example.expensetracker.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.domain.repository.ExpenseRepository
import com.example.expensetracker.domain.model.DailyTransactions
import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.usecase.AddExpenseUseCase
import com.example.expensetracker.domain.usecase.DeleteExpenseUseCase
import com.example.expensetracker.domain.usecase.GetAllCategoriesUseCase
import com.example.expensetracker.domain.usecase.GetAllExpensesUseCase
import com.example.expensetracker.domain.usecase.GetGroupedMonthlyExpensesUseCase
import com.example.expensetracker.domain.usecase.UpdateExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

import java.util.Date
import java.util.Calendar
import java.time.ZoneId

// 为 Date 添加扩展函数
fun Date.toLocalDate(): LocalDate {
    return toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
}

// presentation/screens/home/HomeViewModel.kt
@HiltViewModel
class HomeViewModel @Inject constructor(
    private  val getAllExpensesUseCase: GetAllExpensesUseCase,
    private  val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase,
    private  val addExpenseUseCase: AddExpenseUseCase,
    private val getGroupedMonthlyExpensesUseCase: GetGroupedMonthlyExpensesUseCase
) : ViewModel() {

    val expenses = getAllExpensesUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val totalIncome = expenses.map { transactions ->
        transactions.filter { it.category.type == "income" }.sumOf { it.amount }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        0.0
    )

    val totalExpense = expenses.map { transactions ->
        transactions.filter { it.category.type == "expense" }.sumOf { it.amount }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        0.0
    )

    val categories = getAllCategoriesUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    // 按日期分组的支出
    private val calendar = Calendar.getInstance()

    // 按月份筛选支出
    fun getExpensesByMonth(yearMonth: YearMonth) = expenses.map { list ->
        list.filter { expense ->
            calendar.time = expense.date
            YearMonth.of(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1
            ) == yearMonth
        }.groupBy { expense ->
            expense.date.time.let { it - (it % 86400000) }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyMap()
    )

    // 获取指定日期的支出
    fun getExpensesForDate(date: LocalDate) = expenses.map { expenseList ->
        expenseList.filter { expense ->
            expense.date.toLocalDate() == date
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )



    private var _selectedMonth = MutableStateFlow(YearMonth.now())
    val selectedMonth = _selectedMonth.asStateFlow()

    fun updateSelectedMonth(yearMonth: YearMonth) {
        _selectedMonth.value = yearMonth
    }

    // 获取选中月份的支出
    val monthlyExpenses = combine(selectedMonth, expenses) { yearMonth, expenseList ->
        expenseList.filter { expense ->
            val localDate = expense.date.toLocalDate()
            YearMonth.from(localDate) == yearMonth
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )


    val groupedMonthlyExpenses = getGroupedMonthlyExpensesUseCase(selectedMonth, expenses)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList() // 默认值为空列表
        )



    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            addExpenseUseCase(expense)
        }
    }

    fun updateExpense(expense: Expense){
        viewModelScope.launch {
            updateExpenseUseCase(expense)
        }
    }

    fun deleteExpense(expense: Expense){
        viewModelScope.launch {
            deleteExpenseUseCase(expense)
        }
    }
}