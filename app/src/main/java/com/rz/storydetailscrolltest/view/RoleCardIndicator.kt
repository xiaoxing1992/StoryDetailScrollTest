package com.rz.storydetailscrolltest.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.rz.storydetailscrolltest.R
import li.etc.skycommons.view.ViewUtil

/**
 * 横向滑动的指示器
 * 做了基于recyclerView的联动[bindRecyclerView]，其余的需要自己适配
 */
class RoleCardIndicator @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val bgPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bgRect: RectF = RectF()
    private var radius: Float = 0f
    private val paintIndicator: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mRect: RectF = RectF()
    private var viewWidth: Int = 0
    private var bgColor = Color.parseColor("#e5e5e5")
    private var indicatorColor = Color.parseColor("#ff4646")
    var ratio = 0.5f        //长度比例
        set(value) {
            field = value
            invalidate()
        }
    var progress: Float = 0f    //滑动进度比例
        set(value) {
            field = value
            invalidate()
        }

    init {

        val typedArray: TypedArray = context.obtainStyledAttributes(
            attrs, R.styleable.RoleCardIndicator
        )
        bgColor = typedArray.getColor(R.styleable.RoleCardIndicator_rc_bgColor, bgColor)
        indicatorColor = typedArray.getColor(
            R.styleable.RoleCardIndicator_rc_indicatorColor, indicatorColor
        )
        typedArray.recycle()

        bgPaint.color = bgColor
        bgPaint.style = Paint.Style.FILL
        paintIndicator.color = indicatorColor
        paintIndicator.style = Paint.Style.FILL
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = w
        bgRect.set(0f, 0f, w * 1f, h * 1f)
        radius = h / 2f
    }

    /**
     * 设置指示器背景进度条的颜色
     * @param color 背景色
     */
    fun setBgColor(@ColorInt color: Int) {
        bgPaint.color = color
        invalidate()
    }

    /**
     * 设置指示器的颜色
     * @param color 指示器颜色
     */
    fun setIndicatorColor(@ColorInt color: Int) {
        paintIndicator.color = color
        invalidate()
    }

    /**
     * 绑定recyclerView
     */
    fun bindRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val offsetX = recyclerView.computeHorizontalScrollOffset()
                val range = recyclerView.computeHorizontalScrollRange()
                val extend = recyclerView.computeHorizontalScrollExtent()
                val progress: Float = offsetX * 1.0f / (range - extend)
                this@RoleCardIndicator.progress = progress     //设置滚动距离所占比例
            }
        })

        recyclerView.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            val range = recyclerView.computeHorizontalScrollRange()
            val extend = recyclerView.computeHorizontalScrollExtent()
            val ratio = extend * 1f / range
            this@RoleCardIndicator.ratio = ratio       //设置指示器所占的长度比例
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //绘制背景
        canvas?.drawRoundRect(bgRect, radius, radius, bgPaint)

        //计算指示器的长度和位置
        val leftOffset = viewWidth * (1f - ratio) * progress
        val left = bgRect.left + leftOffset
        val right = left + viewWidth * ratio
        mRect.set(left, bgRect.top, right, bgRect.bottom)

        //绘制指示器
        canvas?.drawRoundRect(mRect, radius, radius, paintIndicator)

        val centerX = left + viewWidth * ratio / 2f
        moveCenterXListener?.invoke(centerX)
    }

    private var moveCenterXListener: ((Float) -> Unit)? = null

    fun setMoveCenterXListener(listener: ((Float) -> Unit)? = null) {
        moveCenterXListener = listener
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val measureWidth = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val measureHeight = MeasureSpec.getSize(heightMeasureSpec)

        val width = if (widthMode == MeasureSpec.EXACTLY) measureWidth else ViewUtil.dip2px(20F)

        val height = if (heightMode == MeasureSpec.EXACTLY) measureHeight else ViewUtil.dip2px(4F)

        setMeasuredDimension(width, height)
    }
}