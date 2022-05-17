package com.iranmobiledev.moodino.utlis

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageLoader() : ImageLoadingService {
    override fun load(context: Context, imagePath: String, container: ImageView) {
        Glide.with(context).load(imagePath).into(container)
    }

    override fun load(context: Context, imageResource: Int, container: ImageView) {
        Glide.with(context).load(imageResource).into(container)
    }
}