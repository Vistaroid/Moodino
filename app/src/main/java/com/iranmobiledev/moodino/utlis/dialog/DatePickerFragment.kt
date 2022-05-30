package com.iranmobiledev.moodino.utlis.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.listener.DatePickerDialogEventListener
import com.iranmobiledev.moodino.utlis.isGreaterThan
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener
import saman.zamani.persiandate.PersianDate


fun getPersianDialog(
    context: Context,
    listener: DatePickerDialogEventListener,
    initDate: PersianDate,
    nightMode: Boolean
): PersianDatePickerDialog {
    val persianDateDialog = PersianDatePickerDialog(context)
        .setPositiveButtonString(context.getString(R.string.ok))
        .setNegativeButton(context.getString(R.string.never_mind))
        .setTodayButton(context.getString(R.string.today))
        .setTodayButtonVisible(true)
        .setMinYear(1300)
        .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
        .setInitDate(initDate.shYear, initDate.shMonth, initDate.shDay)
        .setActionTextColor(Color.GRAY)
        .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
        .setShowInBottomSheet(true)
        .setBackgroundColor(context.getColor(if (nightMode) R.color.gray900 else R.color.white))
        .setPickerBackgroundColor(context.getColor(if (nightMode) R.color.gray900 else R.color.white))
        .setTitleColor(context.getColor(if (nightMode) R.color.white else R.color.black))
        .setListener(object : PersianPickerListener {
            override fun onDateSelected(persianPickerDate: PersianPickerDate?) {
                persianPickerDate?.let {
                    val persianDate = PersianDate()
                    val thisDate = PersianDate()
                    thisDate.shYear = it.persianYear
                    thisDate.shMonth = it.persianMonth
                    thisDate.shDay = it.persianDay
                    if (!persianDate.isGreaterThan(thisDate)) {
                        Toast.makeText(context, context.getString(R.string.not_reached_today), Toast.LENGTH_LONG)
                            .show()
                        persianPickerDate.setDate(
                            persianDate.shYear,
                            persianDate.shMonth,
                            persianDate.shDay
                        )
                        listener.onDateSelected(persianPickerDate)
                    } else
                        listener.onDateSelected(persianPickerDate)
                }
            }

            override fun onDismissed() {
            }
        })
    return persianDateDialog
}