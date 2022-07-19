package com.rz.storydetailscrolltest

import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewTreeLifecycleOwner

public fun View.findViewTreeLifecycleOwner(): LifecycleOwner? = ViewTreeLifecycleOwner.get(this)

fun Number.dp(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        toFloat(), Resources.getSystem().displayMetrics
    )
}

/**
 * dp 转换成 px
 * */
fun Int.dp(): Int {
    val value = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        toFloat(), Resources.getSystem().displayMetrics
    )
    return when {
        value > 0 -> (value + 0.5f).toInt()
        value < 0 -> (value - 0.5f).toInt()
        else -> value.toInt()
    }
}

/**
 * sp 转换成 px
 * */
fun Number.sp(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        toFloat(), Resources.getSystem().displayMetrics
    )
}

/**
 * sp 转换成 px
 * */
fun Int.sp(): Int {
    val value = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        toFloat(), Resources.getSystem().displayMetrics
    )
    return when {
        value > 0 -> (value + 0.5f).toInt()
        value < 0 -> (value - 0.5f).toInt()
        else -> value.toInt()
    }
}
