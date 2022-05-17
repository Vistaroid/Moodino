package com.iranmobiledev.moodino.repository.activity.source

import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.data.ActivityAndCategory
import kotlinx.coroutines.flow.Flow

interface ActivityDataSource {
    fun getAll() : Flow<List<ActivityAndCategory>>
}