package com.example.expensetracker.di
import android.content.Context
import androidx.room.Room
import com.example.expensetracker.data.local.AppDatabase
import com.example.expensetracker.data.local.dao.CategoryDao
import com.example.expensetracker.data.local.dao.ExpenseDao
import com.example.expensetracker.data.repository.CategoryRepositoryMockImpl
import com.example.expensetracker.data.repository.ExpenseRepositoryMockImpl
import com.example.expensetracker.data.repository.CategoryRepository
import com.example.expensetracker.data.repository.CategoryRepositoryRoomImpl
import com.example.expensetracker.data.repository.ExpenseRepository
import com.example.expensetracker.data.repository.ExpenseRepositoryRoomImpl
import dagger.*
import dagger.hilt.*
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.*
import javax.inject.*

// di/RepositoryModule.kt
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
//    @Provides
//    @Singleton
//    fun provideExpenseRepository(): ExpenseRepository {
//        return ExpenseRepositoryMockImpl()
//    }

    @Provides
    @Singleton
    fun provideExpenseRepository(
        expenseDao: ExpenseDao,  // Hilt 会自动注入 ExpenseDao
        categoryDao: CategoryDao // Hilt 会自动注入 CategoryDao
    ): ExpenseRepository {
        return ExpenseRepositoryRoomImpl(expenseDao, categoryDao)
    }

//    @Provides
//    @Singleton
//    fun provideCategoryRepository(): CategoryRepository {
//        return CategoryRepositoryMockImpl()
//    }
    @Provides
    @Singleton
    fun provideCategoryRepository(categoryDao: CategoryDao): CategoryRepository {
        return CategoryRepositoryRoomImpl(categoryDao)
    }


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "expense_tracker_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(db: AppDatabase): CategoryDao {
        return db.categoryDao()
    }

    @Provides
    @Singleton
    fun provideExpenseDao(db: AppDatabase): ExpenseDao {
        return db.expenseDao()
    }
}