package com.iranmobiledev.moodino.listener

import ir.hamsaa.persiandatepicker.api.PersianPickerDate

interface DatePickerDialogEventListener {
    fun onDateSelected(persianPickerDate: PersianPickerDate)
}