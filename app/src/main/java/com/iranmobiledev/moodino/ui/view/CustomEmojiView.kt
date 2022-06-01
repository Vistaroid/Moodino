package com.iranmobiledev.moodino.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.color.MaterialColors
import com.iranmobiledev.moodino.R

class CustomEmojiView(context: Context, attr: AttributeSet): AppCompatImageView(context,attr) {

    var isSelectedEmoji: Boolean= false
    get() = field
    set(value) {
        field= value
        if (value){
            selectEmoji()
        }else{
            deSelectEmoji()
        }
    }

    private var bgTintColor: ColorStateList?= null
    private var tintColor: ColorStateList?= null



    init {

        val nightMode = when (context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> true
            Configuration.UI_MODE_NIGHT_NO -> false
            else -> false
        }

        background= ResourcesCompat.getDrawable(resources,R.drawable.circle_bg,context.theme)
        backgroundTintList= resources.getColorStateList(if (nightMode) R.color.black else R.color.white, context.theme)
        bgTintColor= backgroundTintList
        tintColor= imageTintList
    }

    private fun selectEmoji(){
        backgroundTintList= tintColor
        imageTintList= bgTintColor
        setPadding(5,5,5,5)
    }

    private fun deSelectEmoji(){
        backgroundTintList= bgTintColor
        imageTintList= tintColor
        setPadding(0,0,0,0)
    }

    fun tint(color: Int){
        tintColor = resources.getColorStateList(color, resources.newTheme())
    }


    @ColorInt
    fun Context.getThemeColor(@AttrRes attrRes: Int): Int {
        val materialColor = MaterialColors.getColor(this, attrRes, Color.WHITE)
        if (materialColor< 0) return materialColor

        val resolvedAttr = TypedValue()
        theme.resolveAttribute(attrRes, resolvedAttr, true)
        val colorRes = resolvedAttr.run { if (resourceId != 0) resourceId else data }
        return ContextCompat.getColor(this, colorRes)
    }
}