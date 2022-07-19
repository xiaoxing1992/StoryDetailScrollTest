package com.rz.storydetailscrolltest

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.animation.doOnEnd

class LightUpSimpleDraweeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)

    //private var testPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var path = Path()
    private var pathMeasure = PathMeasure()
    private var paintWidth = 5f.dp()

    private var isAniamtioning = false

    //执行时间
    private var animationTime: Long = 2500L

    private var animator: Animator? = null

    init {
        paint.apply {
            color = Color.BLUE
            style = Paint.Style.STROKE
            strokeWidth = paintWidth
        }
        //testPaint.apply {
        //    color = Color.RED
        //    style = Paint.Style.STROKE
        //    strokeWidth = paintWidth
        //}
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.reset()
        val rectDef = RectF(
            0f + paintWidth / 2f, 0f + paintWidth / 2f, w - paintWidth / 2f, h - paintWidth / 2f
        )
        path.arcTo(rectDef, 225f, 359f)
        path.close()
        pathMeasure.setPath(path, false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //canvas.drawRect(
        //    0f + paintWidth / 2f,
        //    0f + paintWidth / 2f,
        //    width - paintWidth / 2f,
        //    height - paintWidth / 2f,
        //    testPaint
        //)
        //canvas.drawCircle(width / 2f, height / 2f, width / 2f - paintWidth / 2f, paint)

        val dra = Path()
        dra.reset()

        val rectDef = RectF(
            0f + paintWidth / 2f,
            0f + paintWidth / 2f,
            width - paintWidth / 2f,
            height - paintWidth / 2f
        )
        dra.arcTo(rectDef, 225f, 0f)

        //dra.lineTo(0f + paintWidth / 2f, 0f + paintWidth / 2f)
        pathMeasure.setPath(path, false)

        pathMeasure.getSegment(
            0f, pathMeasure.length * progress, dra, true
        )
        //Log.e("LightUp", "onDraw, width: $width  , height: $height")
        if (progress > 0) {
            canvas.drawPath(dra, paint)
        }
    }

    private var progress = 0.0f

    override fun setActivated(activated: Boolean) {
        progress = if (activated) {
            1f
        } else {
            0f
        }
        super.setActivated(activated)
    }

    fun startAnimator(listener: (() -> Unit)? = null) {
        if (isAniamtioning) {
            return
        }
        isAniamtioning = true
        animator?.cancel()
        animator = ValueAnimator.ofFloat(0f, 1f).apply {
            addUpdateListener { animation ->
                progress = animation.animatedValue as Float
                postInvalidate()
            }
            doOnEnd {
                isActivated = true
                isAniamtioning = false
                listener?.invoke()
            }
            duration = animationTime
            start()
        }
    }

    fun setAnimationTime(time: Long) {
        animationTime = time
    }
}