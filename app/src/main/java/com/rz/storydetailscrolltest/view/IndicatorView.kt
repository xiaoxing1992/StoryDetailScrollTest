package com.rz.storydetailscrolltest.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ScreenUtils
import com.rz.storydetailscrolltest.R
import com.rz.storydetailscrolltest.dp

class IndicatorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var radius = DEF_RADIUS
    private var selectColor: Int = DEF_SELECT_COLOR
    private var unSelectColor: Int = DEF_UNSELECT_COLOR
    private var ovalInterval = DEF_INTERVAL //指示器间的间隔
    private var count = 0
    private var listData:MutableList<String> = mutableListOf()
    private var pageIndex = 0
    private val selectPaint = Paint()
    private val unSelectPaint = Paint()


    private var textPaint = TextPaint().apply {
        color = selectColor
        textSize = ConvertUtils.sp2px(9F).toFloat()
    }


    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView)
        radius = a.getDimension(R.styleable.IndicatorView_ri_radius, DEF_RADIUS)
        ovalInterval = a.getDimension(R.styleable.IndicatorView_ri_interval, DEF_INTERVAL)
        selectColor = a.getColor(R.styleable.IndicatorView_ri_select_color, DEF_SELECT_COLOR)
        unSelectColor = a.getColor(R.styleable.IndicatorView_ri_unselect_color, DEF_UNSELECT_COLOR)
        a.recycle()

        textPaint.color = selectColor
        selectPaint.color = selectColor
        unSelectPaint.color = unSelectColor
    }

    fun setupWithViewPager(viewPager: ViewPager,bindList: MutableList<String>) {
        count = viewPager.adapter?.count ?: 0
        listData.clear()
        listData.addAll(bindList)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
            }

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageSelected(position: Int) {
                pageIndex = position
                invalidate()
            }
        })
        invalidate()
    }


    override fun onDraw(canvas: Canvas?) {
        canvas ?: return
        if (count < 1) return
        val diameter = canMaxWidth / count
        val half = (width - diameter * (count) - ovalInterval * (count - 1)) / 2f
        for (i in 0 until count) {
            val left = half + (diameter + ovalInterval) * i
            val top = height / 2f - radius
            val bottom = height / 2f + radius
            val right = left + diameter
            canvas.drawRect(
                left, top, right, bottom, if (i == pageIndex) selectPaint else unSelectPaint
            )
            val text = listData[i]
            val textWidth=textPaint.measureText(text)
            if (i == pageIndex) {
                canvas.drawText(text, left + diameter/2-textWidth/2, top-4.dp(), textPaint)
            }
        }
    }


    val canMaxWidth = ScreenUtils.getScreenWidth() * 0.8f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        setMeasuredDimension(ScreenUtils.getScreenWidth(), (150).toInt())
    }

    companion object {
        private const val DEF_RADIUS = 8f
        private const val DEF_INTERVAL = 0f
        private const val DEF_SELECT_COLOR = Color.RED
        private const val DEF_UNSELECT_COLOR = Color.BLUE
    }
}