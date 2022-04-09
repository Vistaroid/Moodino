package com.iranmobiledev.moodino.utlis

import com.iranmobiledev.moodino.data.Entry

interface EntryEventListener {
    fun addEntry(entry : Entry){

    }
    fun deleteEntry(entry: Entry) : Boolean {
        return false
    }
    fun updateEntry(entry: Entry){

    }
}