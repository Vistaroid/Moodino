package com.iranmobiledev.moodino.utlis

import android.app.Activity
import android.content.Context
import android.widget.ImageView

interface ImageLoadingService {
    fun load(activity: Activity, imagePath : String, container: ImageView)
}