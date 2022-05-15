package com.iranmobiledev.moodino.ui.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
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