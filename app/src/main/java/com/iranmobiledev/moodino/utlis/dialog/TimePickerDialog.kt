package com.iranmobiledev.moodino.utlis.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.callback.TimePickerCallback
import com.iranmobiledev.moodino.data.EntryTime
import com.iranmobiledev.moodino.databinding.TimePickerViewBinding

class TimePickerDialog(private val entryTime: EntryTime? = null): BottomSheetDialogFragment() {
    private lateinit var binding: TimePickerViewBinding
    private var listener: TimePickerCallback? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TimePickerViewBinding.inflate(inflater,container,false)
        binding.timePicker.setIs24HourView(true)
        entryTime?.let {
            setTime(it)
        }
        binding.okTv.setOnClickListener {
            listener?.onTimePickerDataReceived(binding.timePicker.hour,binding.timePicker.minute)
            dismiss()
        }
        binding.cancel.setOnClickListener { dismiss() }
        return binding.root
    }

    fun setListener(listener: TimePickerCallback){
        this.listener = listener
    }
    private fun setTime(entryTime: EntryTime){
        binding.timePicker.hour = Integer.parseInt(entryTime.hour)
        binding.timePicker.minute = Integer.parseInt(entryTime.minutes)
    }
}