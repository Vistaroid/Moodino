package com.iranmobiledev.moodino.repository.more.source

interface MoreDataSource {

    suspend fun savePINLock(pin : String)

    fun isActivePINLock() : Boolean

    fun isActiveFingerPrintLock() : Boolean

    suspend fun setActivePINLock(active : Boolean)

    suspend fun setActiveFingerPrintLock(active : Boolean)

    suspend fun saveTimeReminder(time : String)

    fun getTimeReminder() : String

}