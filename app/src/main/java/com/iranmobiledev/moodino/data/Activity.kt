package com.iranmobiledev.moodino.data

import android.os.Parcelable
import androidx.annotation.LayoutRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "table_activity")
@Parcelize
data class Activity(
    @PrimaryKey(autoGenerate = true)
    var id : Int?,
    val image : Int,
    val title : String,
    val category : String,
) : Parcelable