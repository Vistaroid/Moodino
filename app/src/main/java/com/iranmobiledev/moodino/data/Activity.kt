package com.iranmobiledev.moodino.data

import android.os.Parcelable
import androidx.annotation.LayoutRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Activity(
    @PrimaryKey(autoGenerate = true)
    var activityId : Long?,
    var activityCategoryId : Long,
    val image : Int,
    val title : String,
    var selected: Boolean = false
) : Parcelable