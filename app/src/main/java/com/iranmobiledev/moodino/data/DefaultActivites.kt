package com.iranmobiledev.moodino.data

import com.iranmobiledev.moodino.R

fun activities1() : ActivityList {
    val activity1 = Activity(R.drawable.ic_arrow_bottom, "achievement")
    val activity2 = Activity(R.drawable.ic_achievement, "achievement")
    val activity3 = Activity(R.drawable.ic_achievement, "achievement")
    val activities = listOf(activity1, activity2, activity3)
    return ActivityList(null, "Food", activities)
}
fun activities2() : ActivityList{
    val activity1 = Activity(R.drawable.ic_achievement, "achievement")
    val activity2 = Activity(R.drawable.ic_achievement, "achievement")
    val activity3 = Activity(R.drawable.ic_achievement, "achievement")
    val activities = listOf(activity1, activity2, activity3)
    return ActivityList(null, "Food", activities)
}
fun activities3() : ActivityList {
    val activity1 = Activity(R.drawable.ic_achievement, "achievement")
    val activity2 = Activity(R.drawable.ic_achievement, "achievement")
    val activities = listOf(activity1, activity2)
    return ActivityList(null, "Food", activities)
}