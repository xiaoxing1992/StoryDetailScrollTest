package com.rz.storydetailscrolltest

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.ViewSwitcher
import androidx.core.view.ViewCompat

class DakaFlipView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ViewSwitcher(context, attrs) {

    private var list = mutableListOf<String>()
    private var bindIndex = 0
    var flipListener: ((index: Int) -> Unit)? = null

    private val runnableHelper = ViewRunnableHelper(runnableCallback = {
        if (it) {
            //Log.w("SearchFlipView", "开始运行 轮播")
            postDelayed(bindRunnable, DELAY_MILLIS / 2)
            postDelayed(flipRunnable, DELAY_MILLIS)
        } else {
            //Log.w("SearchFlipView", "停止 轮播")
            removeCallbacks(flipRunnable)
            removeCallbacks(bindRunnable)
        }
    }, extraAllowRunning = {
        return@ViewRunnableHelper list.isNotEmpty()
    })

    private val bindRunnable: Runnable by lazy {
        object : Runnable {
            override fun run() {
                if (runnableHelper.isRunning) {
                    bindIndex++
                    if (bindIndex >= list.size) {
                        bindIndex = 0
                    }
                    bindData(true, bindIndex)
                    postDelayed(this, DELAY_MILLIS)
                }
            }
        }
    }

    private val flipRunnable: Runnable by lazy {
        object : Runnable {
            override fun run() {
                if (runnableHelper.isRunning) {
                    showNext()
                    flipListener?.invoke(bindIndex)
                    postDelayed(this, DELAY_MILLIS)
                }
            }
        }
    }

    init {
        val inflater = LayoutInflater.from(context)
        // 必须添加两个View
        inflater.inflate(R.layout.widget_item_daka_flip_view, this, true)
        inflater.inflate(R.layout.widget_item_daka_flip_view, this, true)
    }

    fun bindView(hints: List<String>, targetIndex: Int? = 0) {
        this.list.clear()
        this.list.addAll(hints)
        if (hints.isNotEmpty()) {
            // 停止
            runnableHelper.stop()
            bindIndex = if (targetIndex != null && targetIndex < hints.size && targetIndex >= 0) {
                targetIndex
            } else {
                0
            }
            bindData(false, bindIndex)
            if (hints.size > 1) {
                // 开始
                runnableHelper.start()
            }
        } else {
            // 停止
            runnableHelper.stop()
        }
    }

    private fun bindData(isNextView: Boolean, index: Int) {
        val view = if (isNextView) nextView else currentView
        (view as TextView).text = list[index]
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        runnableHelper.onAttachedToWindow(this, findViewTreeLifecycleOwner()?.lifecycle)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        runnableHelper.onDetachedFromWindow(findViewTreeLifecycleOwner()?.lifecycle)
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        runnableHelper.onWindowVisibilityChanged(visibility)
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (ViewCompat.isAttachedToWindow(this)) {
            runnableHelper.onVisibilityChanged(this, visibility)
        }
    }

    companion object {
        private const val DELAY_MILLIS = 3000L
    }
}