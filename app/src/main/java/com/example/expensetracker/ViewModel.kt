package com.example.expensetracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionDao: TransactionDao
) : ViewModel() {

    val allTransactions = transactionDao.getAllTransactions()
    val totalIncome = transactionDao.getTotalByType(TransactionType.INCOME)
    val totalExpense = transactionDao.getTotalByType(TransactionType.EXPENSE)

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            transactionDao.insertTransaction(transaction)
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            transactionDao.deleteTransaction(transaction)
        }
    }
}