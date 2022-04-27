package com.iranmobiledev.moodino.utlis

import android.content.Context
import android.content.SharedPreferences

object SharedPref {

    val sharedPreferences : SharedPreferences? = null

    fun create(context: Context) : SharedPreferences{
        return sharedPreferences ?: context.getSharedPreferences("userManager", Context.MODE_PRIVATE)
    }
}