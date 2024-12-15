package com.example.expensetracker

import android.app.Application
import com.example.expensetracker.data.local.dao.CategoryDao
import com.example.expensetracker.data.local.entity.CategoryEntity
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// ExpenseApplication.kt
@HiltAndroidApp
class ExpenseApplication : Application(){

    @Inject
    lateinit var categoryDao: CategoryDao

    override fun onCreate() {
        super.onCreate()

        // 使用协程异步执行初始化操作
        CoroutineScope(Dispatchers.IO).launch {
            initializeCategories()
        }
    }

    private suspend fun initializeCategories() {
        // 初始化数据库分类数据
        val existingCategories = categoryDao.getAllCategories()
//        val existingCategories = categoryDao.getAllCategoriesFlow().firstOrNull() ?: emptyList()  // 如果 Flow 为空，则返回空列表

        if (existingCategories.isEmpty()) {
            val categories = listOf(
                CategoryEntity(id = "1", name = "Food", icon = "ic_food", type = "Expense"),
                CategoryEntity(id = "2", name = "Transportation", icon = "ic_transport", type = "Expense"),
                CategoryEntity(id = "3", name = "Entertainment", icon = "ic_entertainment", type = "Expense"),
                CategoryEntity(id = "4", name = "Salary", icon = "ic_salary", type = "Income"),
                CategoryEntity(id = "5", name = "Investments", icon = "ic_investment", type = "Income")
            )
            categoryDao.insertAll(categories)
        }
    }
}