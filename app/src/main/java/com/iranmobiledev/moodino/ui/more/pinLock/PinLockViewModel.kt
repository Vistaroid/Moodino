package com.iranmobiledev.moodino.ui.more.pinLock

import androidx.lifecycle.viewModelScope
import com.iranmobiledev.moodino.base.BaseViewModel
import com.iranmobiledev.moodino.repository.more.source.MoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PinLockViewModel(private val moreRepository: MoreRepository) : BaseViewModel() {

    fun checkPIN() : Boolean = moreRepository.isActivePINLock()

    fun checkFingerPrint() : Boolean = moreRepository.isActiveFingerPrintLock()

    fun setPinLock(pin : String){
        viewModelScope.launch(Dispatchers.IO){
            moreRepository.savePINLock(pin)
            moreRepository.setActivePINLock(true)
            moreRepository.setActiveFingerPrintLock(true)
        }
    }

    fun offLock(){
        viewModelScope.launch(Dispatchers.IO){
            moreRepository.savePINLock("")
            moreRepository.setActivePINLock(false)
            moreRepository.setActiveFingerPrintLock(false)
        }
    }

    fun setFingerPrintLock(active : Boolean){
        viewModelScope.launch(Dispatchers.IO){
            moreRepository.setActiveFingerPrintLock(active)
        }
    }

    fun setPINLock(active : Boolean){
        viewModelScope.launch(Dispatchers.IO){
            moreRepository.setActivePINLock(active)
        }
    }
}