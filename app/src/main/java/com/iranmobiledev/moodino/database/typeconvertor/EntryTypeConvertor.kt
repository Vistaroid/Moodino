package com.iranmobiledev.moodino.database.typeconvertor

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iranmobiledev.moodino.data.Entry

class EntryTypeConvertor {
    @TypeConverter
    fun fromEntryList(entries : List<Entry>) : String{
        val gson = Gson()
        val type = object : TypeToken<List<Entry>>(){}.type
        return gson.toJson(entries, type)
    }
    @TypeConverter
    fun toEntryList(entries : String) : List<Entry>{
        val gson = Gson()
        val type = object : TypeToken<List<Entry>>(){}.type
        return gson.fromJson(entries, type)
    }
}