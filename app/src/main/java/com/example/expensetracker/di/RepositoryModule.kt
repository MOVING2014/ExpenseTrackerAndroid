package com.example.expensetracker.di
import android.content.Context
import androidx.room.Room
import com.example.expensetracker.data.*
import com.example.expensetracker.repository.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context):AppDatabase{
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "expense_tracker_db"
        ).build()
    }
}