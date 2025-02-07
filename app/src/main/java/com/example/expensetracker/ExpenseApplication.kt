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
                CategoryEntity(id = "1", name = "餐饮", icon = "\uD83C\uDF54", type = "expense"),
                CategoryEntity(id = "2", name = "交通", icon = "\uD83D\uDE97", type = "expense"),
                CategoryEntity(id = "3", name = "购物", icon = "\uD83D\uDED2", type = "expense"),
                CategoryEntity(id = "4", name = "娱乐", icon = "\uD83C\uDFB6", type = "expense"),
                CategoryEntity(id = "5", name = "住房", icon = "\uD83C\uDFE0", type = "expense"),
                CategoryEntity(id = "6", name = "工资", icon = "\uD83D\uDCB0", type = "income"),
                CategoryEntity(id = "7", name = "奖金", icon = "\uD83D\uDCB6", type = "income")


            )
            categoryDao.insertAll(categories)
        }
    }
}