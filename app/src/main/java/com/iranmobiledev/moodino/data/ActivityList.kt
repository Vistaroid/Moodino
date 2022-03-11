package com.iranmobiledev.moodino.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "tbl_activities")

data class ActivityList(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val category : String,
    val activities : List<Activity>
)