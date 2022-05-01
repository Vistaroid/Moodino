package com.iranmobiledev.moodino.ui.calendar.calendarpager

import android.content.Context
import com.iranmobiledev.moodino.R

enum class Language(val code: String, val nativeName: String) {
  FA("fa","فارسی"),
    FA_AF("fa-AF", "دری"),
  EN_IR("en", "English (Iran)");

    val my: String
        get() = when (this) {
         //   CKB -> "%1\$sی %2\$s"
         //   ZH_CN -> "%2\$s 年 %1\$s"
            else -> "%1\$s %2\$s"
        }

  companion object{
      val PERSIAN_DIGITS= charArrayOf('۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹')
     // val English_DIGITS= charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')

      // These are special cases and new ones should be translated in strings.xml of the language
      private val persianCalendarMonths = listOf12Items(
          R.string.farvardin, R.string.ordibehesht, R.string.khordad,
          R.string.tir, R.string.mordad, R.string.shahrivar,
          R.string.mehr, R.string.aban, R.string.azar, R.string.dey,
          R.string.bahman, R.string.esfand
      )
      private val persianCalendarMonthsInPersian = listOf12Items(
          "فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد",
          "شهریور", "مهر", "آبان", "آذر", "دی",
          "بهمن", "اسفند"
      )

      private val persianCalendarMonthsInDari = listOf12Items(
          "حمل", "ثور", "جوزا", "سرطان", "اسد", "سنبله",
          "میزان", "عقرب", "قوس", "جدی", "دلو",
          "حوت"
      )

      private val weekDays = listOf7Items(
          R.string.saturday, R.string.sunday, R.string.monday, R.string.tuesday,
          R.string.wednesday, R.string.thursday, R.string.friday
      )

      private val weekDaysInPersian = listOf7Items(
          "شنبه", "یکشنبه", "دوشنبه", "سه‌شنبه", "چهارشنبه", "پنجشنبه", "جمعه"
      )
      private val weekDaysInitialsInEnglishIran = listOf7Items(
          "Sh", "Ye", "Do", "Se", "Ch", "Pa", "Jo"
      )
  }

    fun getPersianMonths(context: Context): List<String> = when (this) {
        FA, EN_IR -> persianCalendarMonthsInPersian
        FA_AF -> persianCalendarMonthsInDari
        else -> persianCalendarMonths.map(context::getString)
    }

    fun getWeekDays(context: Context): List<String> = when (this) {
        FA, EN_IR, FA_AF -> weekDaysInPersian
      //  NE -> weekDaysInNepali
        else -> weekDays.map(context::getString)
    }

    fun getWeekDaysInitials(context: Context): List<String> = when (this) {
     //   AR -> weekDaysInitialsInArabic
    //    AZB -> weekDaysInitialsInAzerbaijani
    //    TR -> weekDaysInitialsInTurkish
        EN_IR -> weekDaysInitialsInEnglishIran
    //    TG -> weekDaysInitialsInTajiki
    //    NE -> weekDaysInitialsInNepali
   //     ZH_CN -> weekDaysInitialsInChinese
        else -> getWeekDays(context).map { it.substring(0, 1) }
    }
}