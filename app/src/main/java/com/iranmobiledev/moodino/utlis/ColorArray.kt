package com.iranmobiledev.moodino.utlis

import com.github.mikephil.charting.utils.ColorTemplate

object ColorArray {

    val awful = ColorTemplate.rgb("#EB4961")
    val good = ColorTemplate.rgb("#7FAD30")
    val bad = ColorTemplate.rgb("#D69048")
    val meh = ColorTemplate.rgb("#5B91AD")
    val rad = ColorTemplate.rgb("#2DAA8A")

    val colors = mutableListOf(rad, good, meh, bad, awful)
}