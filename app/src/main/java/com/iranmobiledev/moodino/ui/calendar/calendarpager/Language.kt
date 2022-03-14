package com.iranmobiledev.moodino.ui.calendar.calendarpager

enum class Language(val code: String, val nativeName: String) {
  FA("fa","فارسی");

    val my: String
        get() = when (this) {
         //   CKB -> "%1\$sی %2\$s"
         //   ZH_CN -> "%2\$s 年 %1\$s"
            else -> "%1\$s %2\$s"
        }

  companion object{
      val PERSIAN_DIGITS= charArrayOf('۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹')
  }
}