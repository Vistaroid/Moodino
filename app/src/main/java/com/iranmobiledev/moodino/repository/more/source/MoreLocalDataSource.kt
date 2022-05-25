package com.iranmobiledev.moodino.repository.more.source

import android.content.SharedPreferences

const val PIN_LOCK = "pin_locke"
const val ACTIVE_PIN_LOCK = "is_active_pin_locke"
const val ACTIVE_FINGERPRINT_LOCK = "is_active_fingerprint_locke"
const val TIME_REMINDER = "reminder_time"

class MoreLocalDataSource(private val sharedPreferences: SharedPreferences) : MoreDataSource {

    override suspend fun savePINLock(pin: String) {
        sharedPreferences.edit().apply {
            putString(PIN_LOCK , pin)
        }.apply()
    }

    override fun isActivePINLock(): Boolean {
        return sharedPreferences.getBoolean(ACTIVE_PIN_LOCK , false)
    }

    override fun isActiveFingerPrintLock(): Boolean {
        return sharedPreferences.getBoolean(ACTIVE_FINGERPRINT_LOCK , false)
    }

    override suspend fun setActivePINLock(active: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(ACTIVE_PIN_LOCK , active)
        }.apply()
    }

    override suspend fun setActiveFingerPrintLock(active: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(ACTIVE_FINGERPRINT_LOCK , active)
        }.apply()
    }

    override suspend fun saveTimeReminder(time: String) {
        sharedPreferences.edit().apply {
            putString(TIME_REMINDER , time)
        }.apply()
    }

    override fun getTimeReminder(): String {
        return sharedPreferences.getString(TIME_REMINDER , "").toString()
    }

    override fun getPin(): String {
        return sharedPreferences.getString(PIN_LOCK , "").toString()
    }
}