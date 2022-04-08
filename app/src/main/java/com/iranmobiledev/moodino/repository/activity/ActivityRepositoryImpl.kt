package com.iranmobiledev.moodino.repository.activity

import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.repository.activity.source.ActivityLocalDataSource

class ActivityRepositoryImpl(private val localDataSource: ActivityLocalDataSource) : ActivityRepository {

    override fun add(activity: Activity): Long {
        return localDataSource.add(activity)
    }

    override fun delete(activity: Activity): Int {
       return localDataSource.delete(activity)
    }

    override fun update(activity: Activity): Int {
        return localDataSource.update(activity)
    }

    override fun getAll(): List<Activity> {
        return localDataSource.getAll()
    }

}