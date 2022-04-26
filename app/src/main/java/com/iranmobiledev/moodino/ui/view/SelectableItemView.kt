package com.iranmobiledev.moodino.ui.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.iranmobiledev.moodino.R

class SelectableItemView(context: Context, attributeSet: AttributeSet?): AppCompatImageView(context, attributeSet) {
    var mColor: Int = -1
    var mSelected: Boolean = false
    init {
        if(attributeSet != null){
            val attr = context.obtainStyledAttributes(attributeSet,R.styleable.SelectableItemView)
            val color = attr.getColor(R.styleable.SelectableItemView_itemColor,-1)
            val selected = attr.getBoolean(R.styleable.SelectableItemView_selected, false)

            if(color != -1){
                mColor = color
                setColorFilter(color)
            }
            if(selected){
                mSelected = selected
                
            }
            attr.recycle()
        }
    }


    private fun getComplementaryColor(colorToInvert: Int): Int {
        val hsv = FloatArray(3)
        Color.RGBToHSV(
            Color.red(colorToInvert), Color.green(colorToInvert),
            Color.blue(colorToInvert), hsv
        )
        hsv[0] = (hsv[0] + 180) % 360
        return Color.HSVToColor(hsv)
    }
}