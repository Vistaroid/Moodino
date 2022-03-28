package com.iranmobiledev.moodino.utlis

import androidx.lifecycle.MutableLiveData
import androidx.navigation.ActivityNavigator
import com.iranmobiledev.moodino.R

object BottomNavVisibility {
    /**
     * every fragment has to set
     * value to current fragment onAttach override
     */
    val currentFragment = MutableLiveData<Int>(R.id.entriesFragment)

    val fragmentsNeedBottomNav = mutableListOf<Int>(
        R.id.entriesFragment,
        R.id.statsFragment,
        R.id.calenderFragment,
        R.id.moreFragment
    )
}