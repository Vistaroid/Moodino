package com.iranmobiledev.moodino.callback

import ir.hamsaa.persiandatepicker.api.PersianPickerDate

interface DatePickerDialogEventListener {
    fun onDateSelected(persianPickerDate: PersianPickerDate)
}