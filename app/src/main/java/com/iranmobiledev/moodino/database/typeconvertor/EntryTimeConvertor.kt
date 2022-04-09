package com.iranmobiledev.moodino.database.typeconvertor

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.data.EntryTime

class EntryTimeConvertor {
    @TypeConverter
    fun fromEntryTime(entryTime: EntryTime) : String{
        val gson = Gson()
        val type = object : TypeToken<EntryTime>(){}.type
        return gson.toJson(entryTime, type)
    }

    @TypeConverter
    fun toEntryDate(entryTime: String) : EntryTime{
        val gson = Gson()
        val type = object : TypeToken<EntryTime>(){}.type
        return gson.fromJson(entryTime, type)
    }
}