package com.iranmobiledev.moodino.data

import androidx.room.Embedded
import androidx.room.Relation

data class ActivityAndCategory(
    @Embedded val category: Category,
    @Relation(parentColumn = "categoryId", entityColumn = "activityCategoryId")
    val activities: List<Activity>
)