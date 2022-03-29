package com.iranmobiledev.moodino.data

import android.net.Uri
import android.os.Parcelable
import androidx.annotation.LayoutRes
import com.iranmobiledev.moodino.utlis.MoodinoDate
import kotlinx.android.parcel.Parcelize
import saman.zamani.persiandate.PersianDate


//TODO warning some of this can be null while adding a entry ! byTayeb.

@Parcelize
data class Entry(
    val title : String = "",
    var note : String = "",
    val activities : List<Activity>? = null,
    var photo : String? = null,
    @LayoutRes var icon : Int? = null,
    //when entry sent with event bus this field should check.
    var state : EntryState? = null,
    var date : EntryDate? = null
) : Parcelable

@Parcelize
data class EntryDate(
    val second : Int,
    var minute : Int,
    val hours : Int,
    val day : Int,
    val month : Int,
    val year : Int
) : Parcelable