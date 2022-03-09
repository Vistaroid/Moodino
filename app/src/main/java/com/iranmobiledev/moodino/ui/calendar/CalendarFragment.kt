package com.iranmobiledev.moodino.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.data.BottomNavState
import com.iranmobiledev.moodino.databinding.FragmentCalendarBinding
import com.iranmobiledev.moodino.databinding.FragmentEntriesBinding
import org.greenrobot.eventbus.EventBus


class CalendarFragment : BaseFragment() {
    private lateinit var binding : FragmentCalendarBinding

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().post(BottomNavState(true))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }
}