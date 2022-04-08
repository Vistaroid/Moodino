package com.iranmobiledev.moodino.repository.activity

import com.iranmobiledev.moodino.data.Activity

interface ActivityRepository {
    fun add(activity: Activity) : Long
    fun delete(activity: Activity) : Int
    fun update(activity: Activity) : Int
    fun getAll() : List<Activity>
}