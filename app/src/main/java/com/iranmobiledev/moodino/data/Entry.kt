package com.iranmobiledev.moodino.data

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


//TODO warning some of this can be null while adding a entry ! byTayeb.

@Entity(tableName = "table_entry")
@Parcelize
data class Entry(
    @PrimaryKey(autoGenerate = true)
    var id : Long? = null,
    var note : String = "",
    var activities : MutableList<Activity> = mutableListOf(),
    var photoPath : String = "",
    var date : EntryDate? = null,
    var time : EntryTime? = null,
    var emojiValue : Int = 3
) : Parcelable

@Parcelize
data class EntryDate(
    val year : Int,
    val month : Int,
    val day : Int,
) : Parcelable

@Parcelize
data class EntryTime(
    val hour : String,
    val minutes : String
) : Parcelable

