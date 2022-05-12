package com.iranmobiledev.moodino.utlis

import com.iranmobiledev.moodino.R

/**
 * this object used
 * for charts
 */

data class Emoji(
    private val value: Float,
    private val title: String,
    private val image: Int
)

enum class EmojiType{
    SIMPLE,
    CHILD
}

interface EmojiInterface{
    fun getEmoji(): Emoji
}

class SimpleEmoji: EmojiInterface{
    override fun getEmoji(): Emoji {
        TODO("Not yet implemented")
    }
}

class ChileEmoji: EmojiInterface{
    override fun getEmoji(): Emoji {
        TODO("Not yet implemented")
    }
}


object EmojiFactory{

    fun getEmoji(type: EmojiType): EmojiInterface{
     return when(type){
          EmojiType.SIMPLE -> SimpleEmoji()
          EmojiType.CHILD -> ChileEmoji()
      }
    }
}

object Moods {
    val AWFUL = 1f
    val BAD = 2f
    val MEH = 3f
    val GOOD = 4f
    val RAD  = 5f
}

const val AWFUL = R.string.awful
const val BAD = R.string.bad
const val MEH = R.string.meh
const val GOOD = R.string.good
const val RAD = R.string.rad
