package com.android.tokoonlinepnpcleanarchitecture.common.animation

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.android.tokoonlinepnpcleanarchitecture.R

class AnimationLoader(
    private val context: Context
) {

    private var top_to_bottom: Animation
    private var bottom_to_top: Animation
    private var left_to_right: Animation
    private var right_to_left: Animation

    init {
        top_to_bottom = AnimationUtils.loadAnimation(context, R.anim.top_to_bottom)
        bottom_to_top = AnimationUtils.loadAnimation(context, R.anim.bottom_to_top)
        left_to_right = AnimationUtils.loadAnimation(context, R.anim.left_to_right)
        right_to_left = AnimationUtils.loadAnimation(context, R.anim.right_to_left)
    }


    fun leftToRight(view: View) {
        view.startAnimation(left_to_right)
    }

    fun topToBottom(view: View) {
        view.startAnimation(top_to_bottom)
    }

    fun bottomToTop(view: View) {
        view.startAnimation(bottom_to_top)
    }

}