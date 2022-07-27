package com.skyplatanus.crucio.recycler.decoration

import android.graphics.Rect
import android.view.View
import androidx.annotation.IntDef
import androidx.recyclerview.widget.RecyclerView

class ItemSpaceDecoration(
    private val space: Int,
    private val includeStart: Boolean = false,
    private val includeEnd: Boolean = false,
    private val reverseLayout: Boolean = false,
    @Orientation private val orientation: Int = ORIENTATION_HORIZONTAL
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount

        val isFirstItem = position == 0
        val isLastItem = itemCount != null && position == itemCount - 1

        if (orientation == ORIENTATION_HORIZONTAL) {
            outRect.left = if ((!reverseLayout && isFirstItem) || (reverseLayout && isLastItem)) {
                if (includeStart) {
                    space
                } else {
                    0
                }
            } else {
                space / 2
            }
            outRect.right = if ((!reverseLayout && isLastItem) || (reverseLayout && isFirstItem)) {
                if (includeEnd) {
                    space
                } else {
                    0
                }
            } else {
                space / 2
            }
        } else if (orientation == ORIENTATION_VERTICAL) {
            outRect.top = if ((!reverseLayout && isFirstItem) || (reverseLayout && isLastItem)) {
                if (includeStart) {
                    space
                } else {
                    0
                }
            } else {
                space / 2
            }
            outRect.bottom = if ((!reverseLayout && isLastItem) || (reverseLayout && isFirstItem)) {
                if (includeEnd) {
                    space
                } else {
                    0
                }
            } else {
                space / 2
            }
        }
    }

    companion object {
        const val ORIENTATION_HORIZONTAL = 1
        const val ORIENTATION_VERTICAL = 2

        @IntDef(ORIENTATION_HORIZONTAL, ORIENTATION_VERTICAL)
        @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
        annotation class Orientation
    }
}