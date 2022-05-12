package com.iranmobiledev.moodino.ui.calendar.calendarpager

import android.content.Context
import android.util.AttributeSet
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
        if (!entries.isNullOrEmpty()) {
            val distinctList = entries.distinctBy { it.title }
            distinctList.forEach { item ->
                val list = entries.filter { it.title == item.title }
                when (item.emojiValue) {
                    EmojiValue.AWFUL -> binding.rad.setData(list.size, ColorArray.rad)
                    EmojiValue.BAD -> binding.good.setData(list.size, ColorArray.good)
                    EmojiValue.MEH -> binding.meh.setData(list.size, ColorArray.meh)
                    EmojiValue.GOOD -> binding.bad.setData(list.size, ColorArray.bad)
                    EmojiValue.RAD -> binding.awful.setData(list.size, ColorArray.awful)
                }
            }
        } else {
            binding.rad.refresh()
            binding.good.refresh()
            binding.meh.refresh()
            binding.bad.refresh()
            binding.awful.refresh()
        }
    }
}