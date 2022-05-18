package com.iranmobiledev.moodino.listener

import com.iranmobiledev.moodino.data.Activity

interface ActivityItemCallback {
    fun onActivityItemClicked(activity: Activity, selected: Boolean)
}