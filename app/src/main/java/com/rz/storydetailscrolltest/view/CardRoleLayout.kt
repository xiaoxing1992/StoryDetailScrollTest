package com.rz.storydetailscrolltest.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import li.etc.skywidget.cardlayout.CardFrameLayout

class CardRoleLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : CardFrameLayout(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val height = measuredHeight
        Log.e("CardRoleLayout", "onMeasure, height: $height")
        val width = (height * 2f / 3f).toInt()
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        )
    }
}