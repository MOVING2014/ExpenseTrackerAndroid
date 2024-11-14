package com.example.expensetracker.di
import com.example.expensetracker.data.*
import com.example.expensetracker.repository.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import javax.inject.*

// di/RepositoryModule.kt
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideExpenseRepository(): ExpenseRepository {
        return MockExpenseRepository()
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(): CategoryRepository {
        return MockCategoryRepository()
    }
}