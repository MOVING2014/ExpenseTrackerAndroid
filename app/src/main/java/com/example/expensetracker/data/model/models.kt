package com.example.expensetracker.data.model

// Models.kt

import android.annotation.SuppressLint
import java.util.Date

// Category 模型
data class Category(
    val id: String,
    val name: String,
    val icon: String,  // 可选的图标
    val type: String // expense | income
)

// Expense 模型
data class Expense(
    val id: String,
    val category: Category,
    val amount: Double,
    val date: Date,
    var note: String? = null,
    var tags: List<String> = mutableListOf()
) {
    init {
        tags = extractTagsFromNote(note)
    }

    // 从note提取标签
    private fun extractTagsFromNote(note: String?): MutableList<String> {
        if (note.isNullOrEmpty()) return mutableListOf()
        val tagRegex = "#([\\w\\u4e00-\\u9fa5]+)".toRegex()
        return tagRegex.findAll(note)
            .map { it.groupValues[1] }
            .toMutableList()
    }

    // 添加标签
    fun addTag(tag: String) {
//        if (!tags.contains(tag)) {
//            tags.add(tag)
//            updateNoteWithTags()
//        }
    }

    // 删除标签
    fun removeTag(tag: String) {
//        tags.removeAll { it == tag }
//        updateNoteWithTags()
    }

    // 更新note中的标签
    private fun updateNoteWithTags() {
        val pureNote = getNoteWithoutTags()
        val tagString = tags.joinToString(" ") { "#$it" }
        note = listOf(pureNote, tagString)
            .filter { it.isNotEmpty() }
            .joinToString(" ")
    }

    // 获取不含标签的纯note内容
    fun getNoteWithoutTags(): String {
//        return note?.replace("#[\\w\\u4e00-\\u9fa5]+".toRegex(), "")?.trim() ?: ""
        return " "
    }

    // 获取标签列表
//    fun getTags(): List<String> = tags.toList()

    // 获取交易类型（支出或收入）
    fun getType(): String = category.type

    // 判断是否为支出
    fun isExpense(): Boolean = category.type == "expense"

    // 判断是否为收入
    fun isIncome(): Boolean = category.type == "income"

    // 获取格式化的金额（考虑收入和支出）
    @SuppressLint("DefaultLocale")
    fun getFormattedAmount(): String {
        val prefix = if (isIncome()) "+" else "-"
        return "$prefix${String.format("%.2f", kotlin.math.abs(amount))}"
    }
}

// 日支出汇总模型
data class DailyExpenseSummary(
    val date: Date,
    val expenses: List<Expense>
) {
    val totalAmount: Double = expenses.sumOf { it.amount }
    val totalExpense: Double = expenses
        .filter { it.category.type == "expense" }
        .sumOf { kotlin.math.abs(it.amount) }
    val totalIncome: Double = expenses
        .filter { it.category.type == "income" }
        .sumOf { kotlin.math.abs(it.amount) }
}

// 月度汇总接口
data class MonthSummary(
    val month: String,
    val expense: Double,
    val income: Double
)