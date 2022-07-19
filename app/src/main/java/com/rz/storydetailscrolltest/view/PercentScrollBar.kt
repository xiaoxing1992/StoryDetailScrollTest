package com.rz.storydetailscrolltest.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.annotation.FloatRange
import com.rz.storydetailscrolltest.R
import com.rz.storydetailscrolltest.dp

class PercentScrollBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var viewAspectRatio = 0.5F
    private var psBackgroundColor = Color.parseColor("#14000000")
    private var progressColor: Int = Color.parseColor("#45DDEA")
    private var radius = 12f.dp()

    private var percentScrollWidth = 0
    private var percentScrollHeight = 0

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var backgroundRect = RectF()
    private var progressRect = RectF()

    init {
        if (attrs != null) {
            val obtainStyledAttributes = context.obtainStyledAttributes(
                attrs, R.styleable.PercentScrollBar
            )
            viewAspectRatio = obtainStyledAttributes.getFloat(
                R.styleable.PercentScrollBar_ps_progress_aspect_ratio, 0.5F
            )
            psBackgroundColor = obtainStyledAttributes.getColor(
                R.styleable.PercentScrollBar_ps_background_color, Color.parseColor("#14000000")
            )
            progressColor = obtainStyledAttributes.getColor(
                R.styleable.PercentScrollBar_ps_progress_color, Color.parseColor("#45DDEA")
            )
            radius = obtainStyledAttributes.getDimension(
                R.styleable.PercentScrollBar_ps_radius, radius
            )

            obtainStyledAttributes.recycle()
        }
        initPaint()
    }

    private fun initPaint() {
        backgroundPaint.color = psBackgroundColor
        progressPaint.color = progressColor
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val measureWidth = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val measureHeight = MeasureSpec.getSize(heightMeasureSpec)

        val width = if (widthMode == MeasureSpec.EXACTLY) measureWidth else 20.dp()

        val height = if (heightMode == MeasureSpec.EXACTLY) measureHeight else 4.dp()

        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        percentScrollWidth = w
        percentScrollHeight = h

        backgroundRect.set(0F, 0F, w.toFloat(), h.toFloat())
        calculateProgressRect()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            it.drawRoundRect(backgroundRect, radius, radius, backgroundPaint)
            it.drawRoundRect(progressRect, radius, radius, progressPaint)
        }
    }

    fun setProgressAspectRatio(@FloatRange(from = 0.0, to = 1.0) aspectRatio: Float) {
        this.viewAspectRatio = aspectRatio
        calculateProgressRect()
        invalidate()
    }

    fun setProgress(@FloatRange(from = 0.0, to = 1.0) progress: Float) {
        calculateProgressRect(progress)
        invalidate()
    }

    private fun calculateProgressRect(progress: Float = 0F) {
        val ratioWidth = percentScrollWidth * viewAspectRatio
        val spareWidth = percentScrollWidth - ratioWidth

        val left = spareWidth * progress
        progressRect.set(left, 0F, left + ratioWidth, percentScrollHeight.toFloat())
    }
}