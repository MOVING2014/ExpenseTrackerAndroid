package com.example.expensetracker.domain.usecase

import com.example.expensetracker.domain.model.Category
import com.example.expensetracker.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private  val categoryRepository: CategoryRepository
) {
    operator fun invoke(): Flow<List<Category>>{
        return categoryRepository.getAllCategories()
    }
}