package com.iranmobiledev.moodino.ui.calendar.calendarpager

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.databinding.MoodsCountViewBinding
import com.iranmobiledev.moodino.utlis.*
import kotlin.math.roundToInt

class MoodCountView(context: Context, attr: AttributeSet) : LinearLayoutCompat(context, attr) {

    private var binding: MoodsCountViewBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = MoodsCountViewBinding.inflate(inflater, this, true)
    }

    fun setEntries(entries: List<Entry>?,isByDay: Boolean = false) {
        refresh()
        if (!entries.isNullOrEmpty()) {
            when(isByDay){

                false -> {
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

                true -> {
                   val distinctEntries = entries.distinctBy { it.date }
                    var rad = 0
                    var good = 0
                    var meh = 0
                    var bad = 0
                    var awful = 0
                   for (entry in distinctEntries){
                       val dayEntries = entries.filter { entry.date == it.date }
                       when (getAverageMood(dayEntries)) {
                           EmojiValue.RAD -> rad++
                           EmojiValue.GOOD -> good++
                           EmojiValue.MEH -> meh++
                           EmojiValue.BAD -> bad++
                           EmojiValue.AWFUL -> awful++
                       }
                       Log.d("dfvkasd", "setEntries: $rad $good $meh $bad $awful")
                   }
                    binding.rad.setData(rad, ColorArray.rad)
                    binding.good.setData(good, ColorArray.good)
                    binding.meh.setData(meh, ColorArray.meh)
                    binding.bad.setData(bad, ColorArray.bad)
                    binding.awful.setData(awful, ColorArray.awful)
                }
            }
        }
    }

    private fun getAverageMood(entries: List<Entry>): Int {
        val sum: Double = entries.sumOf { it.emojiValue }.toDouble()
        val average: Double = sum / entries.size
        return average.roundToInt()
    }

    private fun refresh() {
        binding.rad.refresh(ColorArray.rad)
        binding.good.refresh(ColorArray.good)
        binding.meh.refresh(ColorArray.meh)
        binding.bad.refresh(ColorArray.bad)
        binding.awful.refresh(ColorArray.awful)
    }
}