package com.iranmobiledev.moodino.utlis

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageLoader() : ImageLoadingService {
    override fun load(activity: Activity, imagePath: String, container: ImageView) {
        Glide.with(activity).load(imagePath).dontAnimate().into(container)
    }
}