package com.android.tokoonlinepnpcleanarchitecture.common

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ImageLoader(
    private val context: Context
) {

    fun loadWithGlide(
        img: Int,
        target: ImageView
    ) {
        val requestOptions = RequestOptions.centerCropTransform()
        Glide.with(context)
            .apply { requestOptions }
            .load(img)
            .into(target)
    }

}