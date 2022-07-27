package com.rz.storydetailscrolltest.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.rz.storydetailscrolltest.R

open class LimitHeightRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

    private var maxHeight = 0

    init {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(
                attrs, R.styleable.LimitHeightRecyclerView
            )
            val minHeight = typedArray.getDimensionPixelSize(
                R.styleable.LimitHeightRecyclerView_lhr_min_height, minimumHeight
            )
            minimumHeight = minHeight
            maxHeight = typedArray.getDimensionPixelSize(
                R.styleable.LimitHeightRecyclerView_lhr_max_height, minimumHeight
            )
            typedArray.recycle()
        }
    }

    fun setMaxHeight(maxHeight: Int) {
        if (this.maxHeight != maxHeight) {
            this.maxHeight = maxHeight
            requestLayout()
        }
    }

    override fun onMeasure(
        widthMeasureSpec: Int, heightMeasureSpec: Int
    ) {
        var heightMeasureSpecResult = heightMeasureSpec
        if (maxHeight > 0) {
            heightMeasureSpecResult = MeasureSpec.makeMeasureSpec(
                maxHeight, MeasureSpec.AT_MOST
            )
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpecResult)
    }
}