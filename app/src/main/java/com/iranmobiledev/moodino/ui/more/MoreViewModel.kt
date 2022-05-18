package com.iranmobiledev.moodino.ui.more

import android.content.SharedPreferences
import com.iranmobiledev.moodino.base.BaseViewModel
import com.iranmobiledev.moodino.utlis.ENGLISH
import com.iranmobiledev.moodino.utlis.LANGUAGE
import com.iranmobiledev.moodino.utlis.PERSIAN

class MoreViewModel(private val sharedPreferences: SharedPreferences) : BaseViewModel() {


    fun getLanguage() : Int = sharedPreferences.getInt(LANGUAGE , ENGLISH)

    fun setLanguage(language : Int) = sharedPreferences.edit().putInt(LANGUAGE , language).apply()
}