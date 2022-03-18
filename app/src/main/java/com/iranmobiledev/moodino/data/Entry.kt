package com.iranmobiledev.moodino.data

import android.net.Uri
import android.os.Parcelable
import androidx.annotation.LayoutRes
import kotlinx.android.parcel.Parcelize


//TODO warning some of this can be null while adding a entry ! byTayeb.

@Parcelize
data class Entry(
    val title : String = "",
    var note : String = "",
    var time : String = "",
    var date : String = "",
    val activities : List<Activity>? = null,
    var photo : Uri? = null,
    @LayoutRes var icon : Int? = null,
    //when entry sent with event bus this field should check.
    var state : EntryState? = null
) : Parcelable