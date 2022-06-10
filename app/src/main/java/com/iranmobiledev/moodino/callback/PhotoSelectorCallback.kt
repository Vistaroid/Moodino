package com.iranmobiledev.moodino.callback

import android.graphics.Bitmap
import android.net.Uri

interface PhotoSelectorCallback {
    fun onSelectorPhotoReceived(path: Uri)
}