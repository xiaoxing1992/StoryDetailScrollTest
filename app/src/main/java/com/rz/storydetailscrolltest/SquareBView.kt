package com.rz.storydetailscrolltest

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class SquareBView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val path = Path()
    private var viewHeight = 0
    private var rightHeight = 0
    private var leftRadius = 0

    private var viewColor: Int = ContextCompat.getColor(context,R.color.teal_200)

    private val mPaint = Paint().apply {
        isAntiAlias = true
        isDither = true
        color =ContextCompat.getColor(context,R.color.teal_200)
    }

    init {
        val a  = context.obtainStyledAttributes(attrs,R.styleable.SquareBView)
        viewHeight = a.getDimension(R.styleable.SquareBView_sq_view_height,0f).toInt()
        rightHeight = a.getDimension(R.styleable.SquareBView_sq_right_height,0f).toInt()
        leftRadius = a.getDimension(R.styleable.SquareBView_sq_left_radius,0f).toInt()

        a.recycle()
    }

    private var viewWidth = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.AT_MOST-> {
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
            MeasureSpec.UNSPECIFIED,MeasureSpec.AT_MOST-> {
                viewHeight = if(viewHeight>0) viewHeight else getsHeight()*2/3
            }
            MeasureSpec.EXACTLY -> {
                viewHeight = if(viewHeight>0) viewHeight else mesHeight//todo Macth
            }
        }
        setMeasuredDimension(viewWidth,viewHeight)
    }

    private var bilv :Float = 0.8f
    private var bilv1 :Float = 0.5f


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.color  =viewColor
        val rightRadius = leftRadius*0.4f
        path.moveTo(0f, 0f)
        path.lineTo(0f, viewHeight.toFloat()-leftRadius)
        path.quadTo(0f,viewHeight.toFloat(),leftRadius.toFloat(),(viewHeight.toFloat()-leftRadius)+leftRadius*bilv)
        path.lineTo(viewWidth.toFloat()-rightRadius*0.7f, viewHeight.toFloat()-rightHeight+rightRadius*0.7f)

        path.quadTo(viewWidth.toFloat()+rightRadius*0.2f,viewHeight.toFloat()-rightHeight+rightRadius*0.4f,viewWidth.toFloat(),viewHeight.toFloat()-rightHeight-rightRadius)


        path.lineTo(viewWidth.toFloat(), 0f)
        path.close()
        canvas.drawPath(path, mPaint)
    }

    fun getsWidth(): Int {
        var resources: Resources = this.resources
        var dm = resources.displayMetrics
        return dm.widthPixels
    }

    fun getsHeight(): Int {
        var resources: Resources = this.resources
        var dm = resources.displayMetrics
        return dm.heightPixels
    }

    private var isNight = false

    fun bindStyle() {
        if(isNight){
            isNight = false
            viewColor = ContextCompat.getColor(context,R.color.teal_200)
        }else{
            isNight = true
            viewColor = ContextCompat.getColor(context, R.color.teal_700)
        }
        invalidate()
    }

    fun changeRadius(radius:Int) {
        leftRadius = radius
        invalidate()
    }
}