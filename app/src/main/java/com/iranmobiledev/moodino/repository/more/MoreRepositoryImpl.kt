package com.iranmobiledev.moodino.repository.more

import com.iranmobiledev.moodino.repository.more.source.MoreLocalDataSource

class MoreRepositoryImpl(private val moreLocalDataSource: MoreLocalDataSource) : MoreRepository {

    override suspend fun savePINLock(pin: String) = moreLocalDataSource.savePINLock(pin)

    override fun isActivePINLock(): Boolean = moreLocalDataSource.isActivePINLock()

    override fun isActiveFingerPrintLock(): Boolean = moreLocalDataSource.isActiveFingerPrintLock()

    override suspend fun setActivePINLock(active: Boolean) = moreLocalDataSource.setActivePINLock(active)

    override suspend fun setActiveFingerPrintLock(active: Boolean) = moreLocalDataSource.setActiveFingerPrintLock(active)
}