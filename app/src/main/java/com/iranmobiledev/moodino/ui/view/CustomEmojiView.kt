package com.iranmobiledev.moodino.ui.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
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
        background= ResourcesCompat.getDrawable(resources,R.drawable.circle_bg,context.theme)
        backgroundTintList= resources.getColorStateList(R.color.white,context.theme)
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
}