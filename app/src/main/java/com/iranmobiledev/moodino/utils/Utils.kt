package com.iranmobiledev.moodino.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.core.content.ContextCompat

val Number.dp : Float get() = this.toFloat() * Resources.getSystem().displayMetrics.density

fun Context.resolveColor(attribute: Int)= TypedValue().let {
    theme.resolveAttribute(attribute,it,true)
    ContextCompat.getColor(this,it.resourceId)
}