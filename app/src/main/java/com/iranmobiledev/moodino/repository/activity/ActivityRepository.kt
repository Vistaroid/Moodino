package com.iranmobiledev.moodino.repository.activity

import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.data.ActivityAndCategory
import kotlinx.coroutines.flow.Flow


interface ActivityRepository {
    fun getAll() : Flow<List<ActivityAndCategory>>
}