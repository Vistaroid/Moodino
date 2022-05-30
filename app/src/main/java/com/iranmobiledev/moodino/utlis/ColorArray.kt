package com.iranmobiledev.moodino.utlis

import com.github.mikephil.charting.utils.ColorTemplate

object ColorArray {

    val awful = ColorTemplate.rgb("#EF162E")
    val good = ColorTemplate.rgb("#7FBF1D")
    val bad = ColorTemplate.rgb("#F58B1E")
    val meh = ColorTemplate.rgb("#4EA2D2")
    val rad = ColorTemplate.rgb("#2DAA8A")

    val colors = mutableListOf(rad, good, meh, bad, awful)
}