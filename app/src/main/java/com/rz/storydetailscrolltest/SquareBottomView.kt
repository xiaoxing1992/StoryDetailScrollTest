package com.rz.storydetailscrolltest

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin

class SquareBottomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var viewColor: Int = ContextCompat.getColor(context, R.color.teal_200)

    private val bezierPointPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    //todo 高度怎么定义
    private var viewHeight = 0
    private var viewWidth = 0

    // 右边倾斜的高度
    private var tiltHeight = 0

    //控件的背景颜色画笔
    private val viewPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()

    //左边底部 贝塞尔起点的高度  高度 同等 圆角度数换算
    private var leftBezierAngleAsHeight = dpToPx(55f)
    private var leftBezierAngle = 0f

    //左边底部贝塞尔圆角P2(结束点)的宽度
    private var leftBezierEndWidth = 0f

    //左边底部贝塞尔圆角P2(结束点)的高度
    private var leftBezierEndHeight = 0f

    //右边底部 贝塞尔起点的高度  高度 同等 圆角度数换算
    private var rightBezierAngleAsHeight = dpToPx(25f)

    //右边底部贝塞尔圆角P2(结束点)的宽度
    private var rightBezierEndWidth = 0f

    //右边底部贝塞尔圆角P2(结束点)的高度
    private var rightBezierEndHeight = 0f

    init {
        context.withStyledAttributes(attrs, R.styleable.SquareBottomView, defStyleAttr, 0) {
            viewHeight = getDimension(R.styleable.SquareBottomView_sb_view_height, 0f).toInt()
            tiltHeight = getDimension(R.styleable.SquareBottomView_sb_tilt_height, 0f).toInt()
            leftBezierAngleAsHeight = getDimension(
                R.styleable.SquareBottomView_sb_left_radius_height, dpToPx(55f).toFloat()
            ).toInt()
            rightBezierAngleAsHeight = getDimension(
                R.styleable.SquareBottomView_sb_right_radius_height, dpToPx(25f).toFloat()
            ).toInt()
        }
        initPaint()
        initData()
    }

    private fun initPaint() {
        viewPaint.apply {
            isDither = true
            color = ContextCompat.getColor(context, R.color.teal_200)
        }
        bezierPointPaint.apply {
            isDither = true
            color = ContextCompat.getColor(context, R.color.bezier_point)
            strokeWidth = dpToPx(12f).toFloat()

        }
    }

    /*
     三角函数
     tanA =  rightHeight / viewWidth
     A = atan(rightHeight / viewWidth)

     tabB = viewWidth /  rightHeight
     B = atan(viewWidth / rightHeight)

  */

    private fun initData() {
        leftBezierAngle = atan(tiltHeight / getsWidth().toFloat())
        //angleB = atan(getsWidth().toFloat() / rightHeight)
        //
        //
        //
        //Log.e(
        //    "SquareBottomView",
        //    "initData\n" + "angleB===" + angleB + "\nrightHeight==" + rightHeight + "\ngetsWidth()==" + getsWidth()
        //)

        Log.e(
            "SquareBottomView",
            "initData\n" + "angleA===" + leftBezierAngle + "\nrightHeight==" + tiltHeight + "\ngetsWidth()==" + getsWidth()
        )

        leftBezierEndWidth = cos(leftBezierAngle) * leftBezierAngleAsHeight
        leftBezierEndHeight = sin(leftBezierAngle) * leftBezierAngleAsHeight

        Log.e(
            "SquareBottomView",
            "initData\nleftBezierEndWidth===$leftBezierEndWidth\nrightBezierEndHeight===$leftBezierEndHeight"
        )

        rightBezierEndWidth = cos(leftBezierAngle) * rightBezierAngleAsHeight
        rightBezierEndHeight = sin(leftBezierAngle) * rightBezierAngleAsHeight
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.AT_MOST -> {
                viewWidth = getsWidth()
            }
            MeasureSpec.EXACTLY -> {
                viewWidth = getsWidth()
            }
            MeasureSpec.UNSPECIFIED -> {
                viewWidth = getsWidth()
            }
        }
        val mesHeight = MeasureSpec.getSize(heightMeasureSpec)
        when (MeasureSpec.getMode(heightMeasureSpec)) {
            MeasureSpec.UNSPECIFIED, MeasureSpec.AT_MOST -> {
                viewHeight = if (viewHeight > 0) viewHeight else getsHeight() * 2 / 3
            }
            MeasureSpec.EXACTLY -> {
                viewHeight = if (viewHeight > 0) viewHeight else mesHeight//todo Macth
            }
        }
        setMeasuredDimension(viewWidth, viewHeight)
    }

    private var smallPointRadius = dpToPx(3f) //测试控制点半径大小 、、todo 后期需要删除

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        viewPaint.color = viewColor

        path.moveTo(0f, 0f)
        path.lineTo(0f, viewHeight.toFloat() - leftBezierAngleAsHeight)

        path.quadTo(
            0f, viewHeight.toFloat(), leftBezierEndWidth, viewHeight.toFloat() - leftBezierEndHeight
        )

        path.lineTo(
            viewWidth.toFloat() - rightBezierEndWidth,
            viewHeight.toFloat() - tiltHeight + rightBezierEndHeight
        )

        path.quadTo(
            viewWidth.toFloat(),
            viewHeight.toFloat() - tiltHeight,
            viewWidth.toFloat(),
            viewHeight.toFloat() - tiltHeight - rightBezierAngleAsHeight
        )

        path.lineTo(viewWidth.toFloat(), 0f)
        path.close()
        canvas.drawPath(path, viewPaint)

        //画 起点、控制点和终点
        canvasBezierPoint(canvas)
    }

    private fun canvasBezierPoint(canvas: Canvas) {

        //左边贝塞尔开始点
        canvas.drawCircle(
            0f,
            viewHeight.toFloat() - leftBezierAngleAsHeight,
            smallPointRadius.toFloat(),
            bezierPointPaint
        )
        //左边贝塞尔控制点
        canvas.drawCircle(
            0f, viewHeight.toFloat(), smallPointRadius.toFloat(), bezierPointPaint
        )
        //左边贝塞尔结束点
        canvas.drawCircle(
            leftBezierEndWidth,
            viewHeight.toFloat() - leftBezierEndHeight,
            smallPointRadius.toFloat(),
            bezierPointPaint
        )

        //右边贝塞尔开始点
        canvas.drawCircle(
            viewWidth.toFloat() - rightBezierEndWidth,
            viewHeight.toFloat() - tiltHeight + rightBezierEndHeight,
            smallPointRadius.toFloat(),
            bezierPointPaint
        )
        //右边贝塞尔控制点
        canvas.drawCircle(
            viewWidth.toFloat(),
            viewHeight.toFloat() - tiltHeight,
            smallPointRadius.toFloat(),
            bezierPointPaint
        )
        //右边贝塞尔结束点
        canvas.drawCircle(
            viewWidth.toFloat(),
            viewHeight.toFloat() - tiltHeight - rightBezierAngleAsHeight,
            smallPointRadius.toFloat(),
            bezierPointPaint
        )
    }

    private fun getsWidth(): Int {
        val resources: Resources = this.resources
        val dm = resources.displayMetrics
        return dm.widthPixels
    }

    private fun getsHeight(): Int {
        val resources: Resources = this.resources
        val dm = resources.displayMetrics
        return dm.heightPixels
    }

    private fun dpToPx(dpValue: Float): Int {//dp转换为px
        val scale = context.resources.displayMetrics.density //获得当前屏幕密度
        return ((dpValue * scale + 0.5f).toInt())
    }

    private var isNight = false

    fun bindStyle() {
        if (isNight) {
            isNight = false
            viewColor = ContextCompat.getColor(context, R.color.teal_200)
        } else {
            isNight = true
            viewColor = ContextCompat.getColor(context, R.color.teal_700)
        }
        invalidate()
    }

    //fun changeRadius(radius: Int) {
    //    leftBezierAngleAsHeight = radius
    //    invalidate()
    //}
}