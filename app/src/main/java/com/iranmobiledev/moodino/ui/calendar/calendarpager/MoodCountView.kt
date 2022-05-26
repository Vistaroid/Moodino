package com.iranmobiledev.moodino.ui.calendar.calendarpager

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.databinding.MoodsCountViewBinding
import com.iranmobiledev.moodino.utlis.*

class MoodCountView(context: Context, attr: AttributeSet): LinearLayoutCompat(context, attr) {

    private var binding: MoodsCountViewBinding
    init {
        val inflater= context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding= MoodsCountViewBinding.inflate(inflater,this,true)
    }

    fun setEntries(entries: List<Entry>?){
        refresh()
        if (!entries.isNullOrEmpty()) {
            val distinctList = entries.distinctBy { it.emojiValue }
            distinctList.forEach { item ->
                val list = entries.filter { it.emojiValue == item.emojiValue }
                when (item.emojiValue) {
                    EmojiValue.RAD -> binding.rad.setData(list.size, ColorArray.rad)
                    EmojiValue.GOOD -> binding.good.setData(list.size, ColorArray.good)
                    EmojiValue.MEH -> binding.meh.setData(list.size, ColorArray.meh)
                    EmojiValue.BAD -> binding.bad.setData(list.size, ColorArray.bad)
                    EmojiValue.AWFUL -> binding.awful.setData(list.size, ColorArray.awful)
                }
            }
        }
    }

    private fun refresh(){
        binding.rad.refresh(ColorArray.rad)
        binding.good.refresh(ColorArray.good)
        binding.meh.refresh(ColorArray.meh)
        binding.bad.refresh(ColorArray.bad)
        binding.awful.refresh(ColorArray.awful)
    }
}