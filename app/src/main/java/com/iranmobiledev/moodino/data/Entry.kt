package com.iranmobiledev.moodino.data

import androidx.annotation.LayoutRes

//TODO warning some of this can be null while adding a entry ! byTayeb.
data class Entry(
    val title : String,
    val note : String,
    val time : String,
    val date : String,
    val activities : List<Activity>,
    val photo : String?,
    @LayoutRes val icon : Int,
    val image : Int,
)