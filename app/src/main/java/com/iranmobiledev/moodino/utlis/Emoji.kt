package com.iranmobiledev.moodino.utlis

import android.content.Context
import com.iranmobiledev.moodino.R

enum class EmojiType{
    SIMPLE,
    CHILD
}

interface EmojiInterface{
    fun getEmoji(value: Float): Emoji
}

data class Emoji(
    val value: Float,
    val title: String,
    val image: Int,  // drawable id
    val color: Int // color id
)

private class SimpleEmoji(private val context: Context): EmojiInterface{
    override fun getEmoji(value: Float): Emoji {
        return when (value) {
            EmojiValue.AWFUL -> Emoji(value, context.getString(R.string.awful), R.drawable.emoji_awful, ColorArray.awful
            )
            EmojiValue.BAD -> Emoji(value, context.getString(R.string.bad), R.drawable.emoji_bad, ColorArray.bad
            )
            EmojiValue.MEH -> Emoji(value, context.getString(R.string.meh), R.drawable.emoji_meh, ColorArray.meh
            )
            EmojiValue.GOOD -> Emoji(value, context.getString(R.string.good), R.drawable.emoji_good, ColorArray.good
            )
            EmojiValue.RAD -> Emoji(value, context.getString(R.string.rad), R.drawable.emoji_rad, ColorArray.rad
            )
            else -> Emoji(value, context.getString(R.string.meh), R.drawable.emoji_meh, ColorArray.meh
            )
        }
    }
}

private class ChildEmoji(private val context: Context): EmojiInterface{
    override fun getEmoji(value: Float): Emoji {
        TODO("Not yet implemented")
    }
}

object EmojiFactory{
    fun create(context: Context): EmojiInterface{
        return when (SharedPref.getStringPref(context, EMOJI_TYPE, EmojiType.SIMPLE.toString())) {
            EmojiType.SIMPLE.toString() -> SimpleEmoji(context)
            EmojiType.CHILD.toString() -> ChildEmoji(context)
            else -> SimpleEmoji(context)
        }
    }
}


object EmojiValue{
    const val AWFUL = 1f
    const val BAD = 2f
    const val MEH = 3f
    const val GOOD = 4f
    const val RAD  = 5f
}