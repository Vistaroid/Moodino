package com.iranmobiledev.moodino.listener

import com.iranmobiledev.moodino.data.Entry

interface EntryEventLister {
    fun add(entry: Entry){

    }
    fun delete(entry: Entry) : Boolean{
        return false
    }
    fun update(entry: Entry){

    }
}