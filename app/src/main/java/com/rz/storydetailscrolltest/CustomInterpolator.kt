package com.rz.storydetailscrolltest

import android.util.Log
import android.view.animation.Interpolator

class CustomInterpolator: Interpolator {
    override fun getInterpolation(input: Float): Float {
        Log.e("CustomInterpolator","getInterpolation,input: $input")
        return input
    }
}