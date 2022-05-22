package com.iranmobiledev.moodino.ui.calendar.calendarpager

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.compose.ui.graphics.Color
import androidx.core.content.res.ResourcesCompat
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.databinding.AverageMoodsDialogItemBinding

class AverageMoodsDialogItemView(context: Context, attr: AttributeSet?): LinearLayoutCompat(context, attr) {


    private lateinit var view: AverageMoodsDialogItemBinding

    companion object{
        const val defaultTitle= "Title"
        const val defaultCount= "0x"
        const val defaultIcon= R.drawable.ic_more_outlined
    }

    init {
        val inflater= context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view= AverageMoodsDialogItemBinding.inflate(inflater,this,true)

        if (attr!= null){
            val styleable = context.obtainStyledAttributes(attr,R.styleable.AverageMoodsDialogItemView)
           view.image.setImageResource(styleable.getResourceId(R.styleable.AverageMoodsDialogItemView_raw_icon,defaultIcon))

            val defColorStateList= resources.getColorStateList(R.color.gray_dark_500, context.theme)
            view.image.imageTintList= styleable.getColorStateList(R.styleable.AverageMoodsDialogItemView_raw_icon_tint)?: defColorStateList

            view.title.text= styleable.getString(R.styleable.AverageMoodsDialogItemView_raw_title)?: defaultTitle

            view.count.text= styleable.getString(R.styleable.AverageMoodsDialogItemView_raw_count)?: defaultCount
            styleable.recycle()
        }
    }

    @SuppressLint("SetTextI18n")
    fun setCount(count: Int?){
        if (count!= null){
            view.count.text= count.toString() + "x"
        }
    }

}