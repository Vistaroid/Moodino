package com.iranmobiledev.moodino.utlis.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.listener.DatePickerDialogEventListener
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener
import saman.zamani.persiandate.PersianDate


fun getPersianDialog(context: Context, listener: DatePickerDialogEventListener, initDate: PersianDate) : PersianDatePickerDialog{
    return PersianDatePickerDialog(context).setPositiveButtonString(context.getString(R.string.ok))
        .setNegativeButton(context.getString(R.string.never_mind))
        .setTodayButton(context.getString(R.string.today))
        .setTodayButtonVisible(true)
        .setMinYear(1300)
        .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
        .setMaxMonth(PersianDatePickerDialog.THIS_MONTH)
        .setMaxDay(PersianDatePickerDialog.THIS_DAY)
        .setInitDate(initDate.shYear, initDate.shMonth, initDate.shDay)
        .setActionTextColor(Color.GRAY)
        .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
        .setShowInBottomSheet(true)
        .setListener(object : PersianPickerListener{
            override fun onDateSelected(persianPickerDate: PersianPickerDate?) {
                persianPickerDate?.let {
                    listener.onDateSelected(persianPickerDate)
                }
            }

            override fun onDismissed() {
            }
        })
}