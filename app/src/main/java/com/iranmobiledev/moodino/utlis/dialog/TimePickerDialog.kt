package com.iranmobiledev.moodino.utlis.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.callback.TimePickerCallback
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.data.EntryTime
import com.iranmobiledev.moodino.databinding.TimePickerViewBinding
import saman.zamani.persiandate.PersianDate

class TimePickerDialog(private val date: PersianDate, private val entryTime: EntryTime? = null) :
    BottomSheetDialogFragment() {
    private lateinit var binding: TimePickerViewBinding
    private var listener: TimePickerCallback? = null
    var currentTime = EntryTime(date.hour.toString(),date.minute.toString())
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TimePickerViewBinding.inflate(inflater, container, false)
        binding.timePicker.setIs24HourView(true)
        entryTime?.let {
            setTime(it)
        }
        binding.okTv.setOnClickListener {
            val todayDate = PersianDate()
            val hour = binding.timePicker.hour
            val minutes = binding.timePicker.minute

            currentTime = EntryTime(hour.toString(), minutes.toString())

            if (date?.shYear == todayDate.shYear &&
                date.shMonth == todayDate.shMonth &&
                date.shDay == todayDate.shDay &&
                hour > todayDate.hour ||
                hour == todayDate.hour && minutes > todayDate.minute){
                Toast.makeText(requireContext(),R.string.not_reached_time,Toast.LENGTH_LONG).show()
                listener?.onTimePickerDataReceived(
                    todayDate.hour,
                    todayDate.minute
                )
            }
            else
                listener?.onTimePickerDataReceived(
                    binding.timePicker.hour,
                    binding.timePicker.minute
                )
            dismiss()
        }
        binding.cancel.setOnClickListener { dismiss() }
        return binding.root
    }

    fun setListener(listener: TimePickerCallback) {
        this.listener = listener
    }

    private fun setTime(entryTime: EntryTime) {
        binding.timePicker.hour = Integer.parseInt(entryTime.hour)
        binding.timePicker.minute = Integer.parseInt(entryTime.minutes)
    }
}