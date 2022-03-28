package com.iranmobiledev.moodino.data

import android.os.Parcelable
import androidx.annotation.LayoutRes
import kotlinx.parcelize.Parcelize


@Parcelize
data class Activity(
    val image : Int,
    val title : String
) : Parcelable