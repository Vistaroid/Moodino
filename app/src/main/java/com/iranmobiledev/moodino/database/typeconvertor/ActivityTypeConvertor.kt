package com.iranmobiledev.moodino.database.typeconvertor

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iranmobiledev.moodino.data.Activity

class ActivityTypeConvertor {

    /**
     * what is type converter ?
     * room can store only primitive types,
     * but for un primitive types like(bitmap,data classes value and ...) you will get error
     * so you have to use this functions and convert them to a primitive type.
     */

    @TypeConverter
    fun fromActivity(activities : List<Activity>) : String{
        val gson = Gson()
        val type = object : TypeToken<List<Activity>>(){}.type
        return gson.toJson(activities, type)
    }
    @TypeConverter
    fun toActivity(activities: String) : List<Activity>{
        val gson = Gson()
        val type = object : TypeToken<List<Activity>>(){}.type
        return gson.fromJson(activities, type)
    }
}