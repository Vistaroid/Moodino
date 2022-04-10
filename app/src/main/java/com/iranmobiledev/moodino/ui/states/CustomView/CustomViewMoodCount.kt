package com.iranmobiledev.moodino.ui.states.CustomView

import android.content.Context
import android.graphics.Color
import android.graphics.ColorFilter
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.iranmobiledev.moodino.R
import org.w3c.dom.Text

class CustomViewMoodCount(context: Context, attributeSet: AttributeSet?) :
    LinearLayout(context, attributeSet) {

    var view: View? = null

    init{

        view = inflate(context, R.layout.mood_count_icon,this)

        if (attributeSet != null) {

            val attr = context.obtainStyledAttributes(attributeSet,R.styleable.CountIcon)
            val icon = attr.getDrawable(R.styleable.CountIcon_MoodIcon)
            val count = attr.getString(R.styleable.CountIcon_MoodCount)
            val tint = attr.getColor(R.styleable.CountIcon_MoodTint,0)
            val moodName = attr.getString(R.styleable.CountIcon_MoodName)
            val background = attr.getDrawable(R.styleable.CountIcon_MoodCountBackground)

            if (icon != null) {
                view?.findViewById<ImageView>(R.id.moodIconImageView)?.setImageDrawable(icon)
            }

            if (count!!.isNotEmpty()){
                view?.findViewById<TextView>(R.id.moodCountTextView)?.text = count
            }

            if (moodName!!.isNotEmpty()){
                view?.findViewById<TextView>(R.id.moodName)?.text = moodName
            }

            if (tint != null){
                view?.findViewById<ImageView>(R.id.moodIconImageView)?.setColorFilter(tint)
                view?.findViewById<TextView>(R.id.moodCountTextView)?.setBackgroundColor(tint)
                view?.findViewById<TextView>(R.id.moodCountTextView)?.background = background
            }
        }
    }
}

