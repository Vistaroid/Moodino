package com.iranmobiledev.moodino.ui.more.reminder

import androidx.lifecycle.viewModelScope
import com.iranmobiledev.moodino.base.BaseViewModel
import com.iranmobiledev.moodino.repository.more.source.MoreRepository
import kotlinx.coroutines.launch

class ReminderViewModel(private val moreRepository: MoreRepository) : BaseViewModel() {


    fun checkReminder() : String{
        return moreRepository.getTimeReminder()
    }

    fun setReminder(time : String){
        viewModelScope.launch {
            moreRepository.saveTimeReminder(time)
        }
    }

    fun setPopupReminder(popup : Boolean){
        viewModelScope.launch {
            moreRepository.setPopupReminder(popup)
        }
    }

    fun checkPopupReminder() : Boolean{
        return moreRepository.checkPopupReminder()
    }
}