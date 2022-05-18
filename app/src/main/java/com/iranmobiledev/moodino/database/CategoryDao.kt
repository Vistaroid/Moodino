package com.iranmobiledev.moodino.database

import androidx.room.*
import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.data.ActivityAndCategory
import com.iranmobiledev.moodino.data.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Transaction
    @Query("SELECT * FROM CATEGORY")
    fun getAll() : Flow<List<ActivityAndCategory>>

    @Insert
    fun add(category: Category): Long
}