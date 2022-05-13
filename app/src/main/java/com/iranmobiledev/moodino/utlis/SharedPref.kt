package com.iranmobiledev.moodino.utlis

import android.content.Context
import com.iranmobiledev.moodino.BuildConfig

object SharedPref {
    fun setPref(context: Context?, key: String, value: Any) {
        val ed =
            context?.getSharedPreferences(BuildConfig.APPLICATION_ID,Context.MODE_PRIVATE)?.edit()
        when(value){
            is String -> ed?.putString(key, value.toString())
            is Int ->  ed?.putInt(key,value.toString().toInt())
            is Boolean -> ed?.putBoolean(key, java.lang.Boolean.parseBoolean(value.toString()))
            is Long -> ed?.putLong(key,value)
        }
        ed?.apply()
    }

    fun getStringPref(context: Context, key: String,default: String): String? {
        return context.getSharedPreferences(BuildConfig.APPLICATION_ID,Context.MODE_PRIVATE)?.getString(key,default)
    }

    fun getIntPref(context: Context, key: String,default: Int): Int? {
        return context.getSharedPreferences(BuildConfig.APPLICATION_ID,Context.MODE_PRIVATE)?.getInt(key,default)
    }

    fun getBooleanPref(context: Context?, key: String,default: Boolean): Boolean? {
        return context?.getSharedPreferences(BuildConfig.APPLICATION_ID,Context.MODE_PRIVATE)?.getBoolean(key,default)
    }

    fun getLongPref(context: Context?, key: String,default: Long): Long? {
        return context?.getSharedPreferences(BuildConfig.APPLICATION_ID,Context.MODE_PRIVATE)?.getLong(key,default)
    }

    fun clearPref(context: Context) {
        context.getSharedPreferences(BuildConfig.APPLICATION_ID,Context.MODE_PRIVATE).edit().clear().apply()
    }

}