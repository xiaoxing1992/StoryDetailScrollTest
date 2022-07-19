package com.rz.storydetailscrolltest

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class ViewRunnableHelper(
    autoStart: Boolean = false,
    private val runnableCallback: (isRunning: Boolean) -> Unit,
    private val extraAllowRunning: (() -> Boolean)? = null
) {
    /**
     * isStart 指 业务逻辑允许运行
     * */
    private var isStart: Boolean = autoStart

    /**
     * isAttached 指 View AttachedToWindow
     * */
    private var isAttachedToWindow: Boolean = false

    /**
     * isViewVisible 指 View 可见（parent 隐藏也会影响可见性）
     * */
    private var isViewVisible: Boolean = false

    /**
     * isWindowVisible 指 View 依附的 Window 可见，极端情况可以与 isViewVisible 区分
     * */
    private var isWindowVisible: Boolean = false

    /**
     * isLifecycleActive 指  onAttachedToWindow 传入的指定生命周期才激活运行
     * */
    private var isLifecycleActive: Boolean? = null

    var isRunning: Boolean = false
        private set

    fun start() {
        isStart = true
        updateRunning()
    }

    fun stop() {
        isStart = false
        updateRunning()
    }

    fun onWindowVisibilityChanged(visibility: Int) {
        isWindowVisible = visibility == View.VISIBLE
        updateRunning()
    }

    /**
     * 【注意！】一定需要  ViewCompat.isAttachedToWindow(view) 需要判断 才能调用
     *  6.0 之前的低版本手机， onVisibilityChanged  方法调用会早于 init 方法调用，导致 ViewRunnableHelper 对象都还没有生成
     * */
    fun onVisibilityChanged(currentView: View, visibility: Int) {
        if (visibility != View.VISIBLE) {
            isViewVisible = false
            updateRunning()
        } else {
            isViewVisible = checkViewVisible(currentView)
            updateRunning()
        }
    }

    private fun checkViewVisible(currentView: View): Boolean {
        var viewVisible: Boolean = currentView.isVisible
        if (!viewVisible) {
            return false
        }
        var viewParent = currentView.parent
        while (viewParent != null) {
            //不包括 window 层的 decorView
            if (viewParent !is ViewGroup) {
                break
            }
            viewVisible = viewParent.isVisible
            if (!viewVisible) {
                return false
            }
            viewParent = viewParent.parent
        }
        return true
    }

    /**
     * 监控 View AttachedToWindow
     *
     * 另外如果传入 lifecycle，  就检查 minActiveState 状态才激活
     * 使用 findViewTreeLifecycleOwner()?.lifecycle 传入 lifecycle
     * */
    fun onAttachedToWindow(
        currentView: View,
        lifecycle: Lifecycle? = null,
        minActiveState: Lifecycle.State = Lifecycle.State.RESUMED,
    ) {
        isViewVisible = checkViewVisible(currentView)

        if (lifecycle != null) {
            val observer = InternalLifecycleObserver(minActiveState)
            internalObserver = observer
            lifecycle.addObserver(observer)
        }
        isAttachedToWindow = true
        updateRunning()
    }

    /**
     * 监控 View onDetachedFromWindow
     *
     * 如果 onAttachedToWindow 传入了 lifecycle ，需要对应解注册
     * 使用 findViewTreeLifecycleOwner()?.lifecycle 传入 lifecycle
     * */
    fun onDetachedFromWindow(lifecycle: Lifecycle? = null) {
        internalObserver?.let {
            lifecycle?.removeObserver(it)
        }
        isAttachedToWindow = false
        updateRunning()
    }

    private var internalObserver: InternalLifecycleObserver? = null

    private inner class InternalLifecycleObserver(private val minActiveState: Lifecycle.State) :
        LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            isLifecycleActive = source.lifecycle.currentState.isAtLeast(minActiveState)
            updateRunning()
        }
    }

    fun updateRunning() {
        val extra = extraAllowRunning?.invoke() ?: true
        val lifecycleActive = isLifecycleActive ?: true

        val running = isStart && isAttachedToWindow && isViewVisible && isWindowVisible && lifecycleActive && extra
        if (running != isRunning) {
            runnableCallback.invoke(running)
            isRunning = running
        }
    }
}