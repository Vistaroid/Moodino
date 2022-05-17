package com.iranmobiledev.moodino.database

import androidx.room.Dao
import androidx.room.Insert
import com.iranmobiledev.moodino.data.Activity

@Dao
interface ActivityDao {
    @Insert
    fun addActivity(activity: Activity): Long
}