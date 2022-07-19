package com.rz.storydetailscrolltest

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout

class CardLightUpFragmentLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)

    //private var testPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var path = Path()
    private var pathMeasure = PathMeasure()
    private var paintWidth = 2f.dp()

    private var rectRadius = 10f.dp()

    //直径
    private var rectDiameter = rectRadius * 2

    //画笔的1/2
    private var paintHalfWidth = paintWidth / 2f

    private var value: Float = 0f
    private var isAniamtioning = false

    //是否点亮
    private var isLightUp: Boolean = false

    //执行时间
    private var animationTime: Long = 2500L

    init {
        setWillNotDraw(false)
        clipToOutline = true
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(
                    0, 0, view.width, view.height, 0f
                )
            }
        }

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
            0f + paintHalfWidth,
            0f + paintHalfWidth,
            0f + rectDiameter + paintHalfWidth,
            rectDiameter - paintHalfWidth
        )
        path.arcTo(rectDef, 225f, 45f)

        rectDef.offset(width.toFloat() - rectDiameter - paintWidth, 0f)

        path.arcTo(rectDef, 270f, 90f)

        rectDef.offset(0f, height.toFloat() - rectDiameter)

        path.arcTo(rectDef, 0f, 90f)

        rectDef.offset(-(width.toFloat() - rectDiameter - paintWidth), 0f)

        path.arcTo(rectDef, 90f, 90f)

        rectDef.offset(0f, -(height.toFloat() - rectDiameter))

        path.arcTo(rectDef, 180f, 45f)

        path.close()

        pathMeasure.setPath(path, false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //Log.e("CardLightUp", "onDraw, width: $width  , height: $height")
        //canvas.drawRoundRect(
        //    0f + paintHalfWidth,
        //    0f + paintHalfWidth,
        //    width - paintHalfWidth,
        //    height - paintHalfWidth,
        //    rectRadius,
        //    rectRadius,
        //    testPaint
        //)

        val dra = Path()
        dra.reset()
        //dra.lineTo(0f + paintHalfWidth, 0f + paintHalfWidth)
        pathMeasure.setPath(path, false)

        pathMeasure.getSegment(
            0f, if (isLightUp) pathMeasure.length else pathMeasure.length * value, dra, true
        )

        canvas.drawPath(dra, paint)
    }

    fun startAnimator() {
        if (isLightUp) {
            return
        }
        if (isAniamtioning) {
            return
        }
        isAniamtioning = true
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.duration = animationTime
        animator.addUpdateListener { animation ->
            value = animation.animatedValue as Float
            postInvalidate()
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                isAniamtioning = false
            }
        })
        animator.start()
    }

    fun setIsLightUp(isLight: Boolean = false) {
        isLightUp = isLight
        postInvalidate()
    }

    fun setAnimationTime(time: Long) {
        animationTime = time
    }
}