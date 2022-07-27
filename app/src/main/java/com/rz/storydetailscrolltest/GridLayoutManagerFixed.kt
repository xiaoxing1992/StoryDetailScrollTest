package com.rz.storydetailscrolltest

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridLayoutManagerFixed : GridLayoutManager {

    constructor(context: Context?, spanCount: Int) : super(context, spanCount)

    constructor(
        context: Context?, spanCount: Int, orientation: Int, reverseLayout: Boolean
    ) : super(
        context, spanCount, orientation, reverseLayout
    )

    constructor(
        context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onLayoutChildren(
        recycler: RecyclerView.Recycler, state: RecyclerView.State
    ) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}