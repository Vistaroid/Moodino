package com.iranmobiledev.moodino.ui.splashScreen

import com.iranmobiledev.moodino.base.BaseViewModel
import com.iranmobiledev.moodino.repository.more.source.MoreRepository

class SplashViewModel(private val moreRepository: MoreRepository) : BaseViewModel() {

    fun checkLogin() : Boolean = moreRepository.isActivePINLock()

    fun pin() : String = moreRepository.getPin()

    fun checkFingerPrint() : Boolean = moreRepository.isActiveFingerPrintLock()

}