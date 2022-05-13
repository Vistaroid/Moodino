package com.iranmobiledev.moodino.utlis

import android.content.Context
import android.content.SharedPreferences
import com.iranmobiledev.moodino.R
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

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
            EmojiValue.AWFUL -> Emoji(value, context.getString(R.string.awful), R.drawable.emoji_awful, R.color.awful_color
            )
            EmojiValue.BAD -> Emoji(value, context.getString(R.string.bad), R.drawable.emoji_bad, R.color.bad_color
            )
            EmojiValue.MEH -> Emoji(value, context.getString(R.string.meh), R.drawable.emoji_meh, R.color.meh_color
            )
            EmojiValue.GOOD -> Emoji(value, context.getString(R.string.good), R.drawable.emoji_good, R.color.good_color
            )
            EmojiValue.RAD -> Emoji(value, context.getString(R.string.rad), R.drawable.emoji_rad, R.color.rad_color
            )
            else -> Emoji(value, context.getString(R.string.meh), R.drawable.emoji_meh, R.color.meh_color
            )
        }
    }
}

private class ChildEmoji(private val context: Context): EmojiInterface{
    override fun getEmoji(value: Float): Emoji {
        TODO("Not yet implemented")
    }
}

object EmojiFactory : KoinComponent{
    fun create(context: Context): EmojiInterface{
        val sharedPref : SharedPreferences by inject()
        return when (sharedPref.getString(EMOJI_TYPE, EmojiType.SIMPLE.toString())) {
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