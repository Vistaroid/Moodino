package com.iranmobiledev.moodino.ui.more.timer

import androidx.lifecycle.viewModelScope
import com.iranmobiledev.moodino.base.BaseViewModel
import com.iranmobiledev.moodino.repository.more.MoreRepository
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
}