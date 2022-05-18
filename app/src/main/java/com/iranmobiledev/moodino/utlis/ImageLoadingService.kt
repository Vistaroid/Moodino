package com.iranmobiledev.moodino.utlis

import android.content.Context
import android.widget.ImageView

interface ImageLoadingService {
    fun load(context: Context, imagePath : String, container: ImageView)
    fun load(context: Context, imageResource: Int, container: ImageView)
}