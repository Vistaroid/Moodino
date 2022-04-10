package com.iranmobiledev.moodino.data

import android.os.Parcelable
import androidx.annotation.LayoutRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


//TODO warning some of this can be null while adding a entry ! byTayeb.

@Entity(tableName = "table_entry")
@Parcelize
data class Entry(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    var title : String = "",
    var note : String = "",
    var activities : List<Activity> = ArrayList(),
    var photoPath : String = "",
    @LayoutRes var icon : Int = 0,
    var date : EntryDate? = null,
    var time : EntryTime? = null
) : Parcelable

@Parcelize
data class EntryDate(
    val year : Int,
    val month : Int,
    val day : Int,
) : Parcelable

@Parcelize
data class EntryTime(
    val hour : Int,
    val minutes : Int
) : Parcelable