package com.iranmobiledev.moodino.utlis

import android.content.Context
import com.iranmobiledev.moodino.R

enum class EmojiType{
    SIMPLE,
    CHILD
}

interface EmojiInterface{
    fun getEmoji(value: Int): Emoji
}

data class Emoji(
    val value: Int,
    val title: String,
    val image: Int,  // drawable id
    val color: Int // color id
)

private class SimpleEmoji(private val context: Context): EmojiInterface{
    override fun getEmoji(value: Int): Emoji {
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
    override fun getEmoji(value: Int): Emoji {
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

fun getEmoji(context: Context,emojiValue: Int): Emoji{
    return EmojiFactory.create(context).getEmoji(emojiValue)
}

object EmojiValue{
    const val AWFUL = 1
    const val BAD = 2
    const val MEH = 3
    const val GOOD = 4
    const val RAD  = 5
}