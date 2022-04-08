package com.iranmobiledev.moodino.database

import androidx.room.*
import com.iranmobiledev.moodino.data.Activity

@Dao
interface ActivitiesDao {
    @Insert
    fun add(activity : Activity) : Long

    @Update
    fun update(activity : Activity) : Int

    @Delete
    fun delete(activity : Activity) : Int

    @Query("SELECT * FROM table_activity")
    fun getAll() : List<Activity>
}