package com.iranmobiledev.moodino.database

import androidx.room.*
import com.iranmobiledev.moodino.data.ActivityList

@Dao
interface ActivitiesDao {
    @Insert
    suspend fun add(activityList: ActivityList) : Long

    @Update
    suspend fun update(activityList: ActivityList) : Int

    @Delete
    suspend fun delete(activityList: ActivityList) : Int

    @Query("SELECT * FROM tbl_activities")
    suspend fun getAll() : List<ActivityList>
}