package com.iranmobiledev.moodino.ui.more

import android.content.SharedPreferences
import com.iranmobiledev.moodino.base.BaseViewModel
import com.iranmobiledev.moodino.utlis.*

class MoreViewModel(private val sharedPreferences: SharedPreferences) : BaseViewModel() {


    fun getLanguage() : Int = sharedPreferences.getInt(LANGUAGE , SYSTEM_DEFAULT)

    fun setLanguage(language : Int) = sharedPreferences.edit().putInt(LANGUAGE , language).apply()

    fun getMode() : Int = sharedPreferences.getInt(MODE_THEME , SYSTEM_DEFAULT)

    fun setMode(mode : Int) = sharedPreferences.edit().putInt(MODE_THEME , mode).apply()
}