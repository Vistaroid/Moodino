package com.iranmobiledev.moodino.callback

import com.iranmobiledev.moodino.data.Activity

interface ActivityItemCallback {
    fun onActivityItemClicked(activity: Activity, selected: Boolean)
}