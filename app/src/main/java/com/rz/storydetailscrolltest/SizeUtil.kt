package com.rz.storydetailscrolltest

import android.content.Context

object SizeUtil {

    fun dpToPx(context: Context, dpValue: Float): Int {//dp转换为px
        val scale = context.resources.displayMetrics.density;//获得当前屏幕密度
        return ((dpValue * scale + 0.5f).toInt())
    }
}