package com.iranmobiledev.moodino.ui.more

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.iranmobiledev.moodino.R

class ViewItemMore(context: Context, attributeSet: AttributeSet?) : FrameLayout(context , attributeSet) {

    var view: View? = null

    init {
        view = inflate(context, R.layout.view_more_item, this)

        if (attributeSet != null) {

            val attr = context.obtainStyledAttributes(attributeSet, R.styleable.ItemMore)
            val title = attr.getString(R.styleable.ItemMore_ItemMore_title)
            val icon = attr.getDrawable(R.styleable.ItemMore_ItemMore_icon)

            if (title!!.isNotEmpty())
                view?.findViewById<TextView>(R.id.tv_itemMore_title)?.text = title

            if (icon != null)
                view?.findViewById<ImageView>(R.id.iv_itemMore_icon)?.setImageDrawable(icon)


            attr.recycle()
        }
    }
}