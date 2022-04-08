package com.iranmobiledev.moodino.repository.activity.source

import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.database.ActivitiesDao

class ActivityLocalDataSource(private val activityDao : ActivitiesDao) : ActivityDataSource {
    override fun add(activity: Activity) : Long{
        return activityDao.add(activity)
    }

    override fun delete(activity: Activity) : Int{
        return activityDao.delete(activity)
    }

    override fun update(activity: Activity) : Int{
        return activityDao.update(activity)
    }

    override fun getAll(): List<Activity> {
        return activityDao.getAll()
    }
}