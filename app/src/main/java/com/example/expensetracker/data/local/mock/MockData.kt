package com.example.expensetracker.data.local.mock
import com.example.expensetracker.domain.model.Category
import com.example.expensetracker.domain.model.Expense

// data/mock/MockData.kt
import java.util.Date
import java.util.UUID

object MockData {
    // 支出类别
    private val expenseCategories = listOf(
        Category(
            id = "exp-1",
            name = "餐饮",
            icon = "restaurant",
            type = "expense"
        ),
        Category(
            id = "exp-2",
            name = "交通",
            icon = "directions_car",
            type = "expense"
        ),
        Category(
            id = "exp-3",
            name = "购物",
            icon = "shopping_bag",
            type = "expense"
        ),
        Category(
            id = "exp-4",
            name = "娱乐",
            icon = "sports_esports",
            type = "expense"
        ),
        Category(
            id = "exp-5",
            name = "住房",
            icon = "house",
            type = "expense"
        )
    )

    // 收入类别
    private val incomeCategories = listOf(
        Category(
            id = "inc-1",
            name = "工资",
            icon = "account_balance_wallet",
            type = "income"
        ),
        Category(
            id = "inc-2",
            name = "奖金",
            icon = "card_giftcard",
            type = "income"
        ),
        Category(
            id = "inc-3",
            name = "理财",
            icon = "trending_up",
            type = "income"
        )
    )

    // 所有类别
    val categories = expenseCategories + incomeCategories

    // 生成模拟支出数据
    val expenses = listOf(
        Expense(
            id = UUID.randomUUID().toString(),
            category = expenseCategories[0], // 餐饮
            amount = 35.0,
            date = Date(),
            note = "午餐 - 快餐",
            tags = mutableListOf("工作日", "外卖")
        ),
        Expense(
            id = UUID.randomUUID().toString(),
            category = expenseCategories[0], // 餐饮
            amount = 188.0,
            date = Date(System.currentTimeMillis() - 86400000), // 昨天
            note = "晚餐 - 火锅",
            tags = mutableListOf("聚餐", "周末")
        ),
        Expense(
            id = UUID.randomUUID().toString(),
            category = expenseCategories[1], // 交通
            amount = 5.0,
            date = Date(),
            note = "地铁上班",
            tags = mutableListOf("工作", "通勤")
        ),
        Expense(
            id = UUID.randomUUID().toString(),
            category = expenseCategories[2], // 购物
            amount = 299.0,
            date = Date(System.currentTimeMillis() - 172800000), // 前天
            note = "新衣服",
            tags = mutableListOf("服装", "周末")
        ),
        Expense(
            id = UUID.randomUUID().toString(),
            category = expenseCategories[3], // 娱乐
            amount = 98.0,
            date = Date(System.currentTimeMillis() - 259200000), // 3天前
            note = "电影票",
            tags = mutableListOf("电影", "约会")
        ),
        Expense(
            id = UUID.randomUUID().toString(),
            category = expenseCategories[4], // 住房
            amount = 2500.0,
            date = Date(System.currentTimeMillis() - 432000000), // 5天前
            note = "房租",
            tags = mutableListOf("固定支出", "月付")
        ),
        // 收入记录
        Expense(
            id = UUID.randomUUID().toString(),
            category = incomeCategories[0], // 工资
            amount = 12000.0,
            date = Date(System.currentTimeMillis() - 518400000), // 6天前
            note = "9月工资",
            tags = mutableListOf("固定收入", "月薪")
        ),
        Expense(
            id = UUID.randomUUID().toString(),
            category = incomeCategories[1], // 奖金
            amount = 2000.0,
            date = Date(System.currentTimeMillis() - 604800000), // 7天前
            note = "季度奖金",
            tags = mutableListOf("额外收入", "季度")
        ),
        Expense(
            id = UUID.randomUUID().toString(),
            category = incomeCategories[2], // 理财
            amount = 500.0,
            date = Date(System.currentTimeMillis() - 691200000), // 8天前
            note = "基金收益",
            tags = mutableListOf("投资", "被动收入")
        ),
        Expense(
            id = UUID.randomUUID().toString(),
            category = incomeCategories[2], // 理财
            amount = 500.0,
            date = Date(System.currentTimeMillis() - 691200000), // 8天前
            note = "基金收益",
            tags = mutableListOf("投资", "被动收入")
        ),
        Expense(
            id = UUID.randomUUID().toString(),
            category = incomeCategories[2], // 理财
            amount = 500.0,
            date = Date(System.currentTimeMillis() - 691200000), // 8天前
            note = "基金收益",
            tags = mutableListOf("投资", "被动收入")
        ),
        Expense(
            id = UUID.randomUUID().toString(),
            category = incomeCategories[2], // 理财
            amount = 500.0,
            date = Date(System.currentTimeMillis() - 691200000), // 8天前
            note = "基金收益",
            tags = mutableListOf("投资", "被动收入")
        ),
        Expense(
            id = UUID.randomUUID().toString(),
            category = incomeCategories[2], // 理财
            amount = 500.0,
            date = Date(System.currentTimeMillis() - 691200000 * 4), // 8天前
            note = "基金收益",
            tags = mutableListOf("投资", "被动收入")
        )
    )

    // 辅助函数：获取指定类型的支出
    fun getExpensesByType(type: String): List<Expense> {
        return expenses.filter { it.category.type == type }
    }

    // 辅助函数：获取指定类型的类别
    fun getCategoriesByType(type: String): List<Category> {
        return categories.filter { it.type == type }
    }

    // 辅助函数：获取指定日期范围的支出
    fun getExpensesByDateRange(startDate: Date, endDate: Date): List<Expense> {
        return expenses.filter {
            it.date.time >= startDate.time && it.date.time <= endDate.time
        }
    }

    // 辅助函数：按类别统计支出
    fun getExpensesByCategory(categoryId: String): List<Expense> {
        return expenses.filter { it.category.id == categoryId }
    }
}



