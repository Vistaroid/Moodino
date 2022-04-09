package com.iranmobiledev.moodino.repository.activity.source

import com.iranmobiledev.moodino.data.Activity

interface ActivityDataSource {
    fun add(activity: Activity) : Long
    fun delete(activity: Activity) : Int
    fun update(activity: Activity) : Int
    fun getAll() : List<Activity>
}