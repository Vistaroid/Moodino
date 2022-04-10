package com.iranmobiledev.moodino.ui.states.CustomView

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.iranmobiledev.moodino.R

class CustomViewAchievement(context:Context, attributeSet: AttributeSet?) :LinearLayout(context,attributeSet){

    var view : View? = null

    init {
        view = inflate(context, R.layout.achievement_icon,this)

        if (attributeSet != null) {

            val attr = context.obtainStyledAttributes(attributeSet,R.styleable.AchievementIcon)
            val icon = attr.getDrawable(R.styleable.AchievementIcon_Icon)
            val firstStarTint = attr.getColor(R.styleable.AchievementIcon_FirstStarTint,0)
            val secondStarTint = attr.getColor(R.styleable.AchievementIcon_SecondStarTint,0)
            val thirdStarTint = attr.getColor(R.styleable.AchievementIcon_ThirdStarTint,0)
            val name = attr.getString(R.styleable.AchievementIcon_Name)

            if (icon != null) {
                view?.findViewById<ImageView>(R.id.achievementIcon)?.setImageDrawable(icon)
            }

            if (name!!.isNotEmpty()){
                view?.findViewById<TextView>(R.id.achievementName)?.text = name
            }

            view?.findViewById<ImageView>(R.id.achievementFirstStar)?.setColorFilter(firstStarTint)
            view?.findViewById<ImageView>(R.id.achievementSecondStar)?.setColorFilter(secondStarTint)
            view?.findViewById<ImageView>(R.id.achievementThirdStar)?.setColorFilter(thirdStarTint)
        }
    }
}