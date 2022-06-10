package com.iranmobiledev.moodino.utlis

import android.content.Context
import android.net.Uri
import android.widget.ImageView

interface ImageLoadingService {
    fun load(context: Context, imagePath : String, container: ImageView)
    fun load(context: Context, imageResource: Int, container: ImageView)
    fun load(context: Context, imageResource: Uri, container: ImageView)
    fun remove(context: Context, view: ImageView)
}