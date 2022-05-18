package com.iranmobiledev.moodino.repository.activity.source

import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.data.ActivityAndCategory
import com.iranmobiledev.moodino.database.ActivityDao
import com.iranmobiledev.moodino.database.CategoryDao
import kotlinx.coroutines.flow.Flow

class ActivityLocalDataSource(private val categoryDao : CategoryDao) : ActivityDataSource {
    override fun getAll(): Flow<List<ActivityAndCategory>> {
        return categoryDao.getAll()
    }

}