package com.rz.storydetailscrolltest

import android.animation.TypeEvaluator
import android.util.Log

class CustomEvaluator : TypeEvaluator<Float> {
    override fun evaluate(fraction: Float, startValue: Float, endValue: Float): Float {

        val scale = fraction / 0.67f
        val test = fraction / 0.83f

        //Log.e(
        //    "CustomEvaluator",
        //    "evaluate,fraction: $fraction startValue: $startValue endValue: $endValue scale: $scale"
        //)
        var result = 0f
        if (0.18f >= scale && scale > 0f) {
            result = (endValue - startValue) * scale
        } else if (0.3f >= scale && scale > 0.18f) {
            result = (endValue - 30f) * scale
        } else if (0.42f >= scale && scale > 0.3f) {
            result = (endValue - 6f) * scale
        } else if (0.54f >= scale && scale > 0.42f) {
            result = (endValue - 20f) * scale
        } else if (1.2f >= scale && scale > 0.54f) {
            result = (endValue - 20f) * scale
        } else if (1.3f >= scale && scale > 1.2f) {
            result = (endValue - 20f) * scale
        } else if (1.5f >= scale && scale > 1.3f) {
            result = 1 - (endValue - startValue) * scale
        }
        Log.e(
            "CustomEvaluator",
            "evaluate,fraction: $fraction startValue: $startValue endValue: $endValue scale: $scale result: $result"
        )
        return test*test
    }
}