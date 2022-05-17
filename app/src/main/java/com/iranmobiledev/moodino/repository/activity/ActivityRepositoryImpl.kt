package com.iranmobiledev.moodino.repository.activity

import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.data.ActivityAndCategory
import com.iranmobiledev.moodino.repository.activity.source.ActivityLocalDataSource
import kotlinx.coroutines.flow.Flow

class ActivityRepositoryImpl(private val localDataSource: ActivityLocalDataSource) : ActivityRepository {

    override fun getAll(): Flow<List<ActivityAndCategory>> {
        return localDataSource.getAll()
    }

}