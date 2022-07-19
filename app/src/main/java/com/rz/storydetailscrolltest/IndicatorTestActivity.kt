package com.rz.storydetailscrolltest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import com.blankj.utilcode.util.ScreenUtils
import com.rz.storydetailscrolltest.databinding.ActivityInducatorTestBinding

class IndicatorTestActivity : AppCompatActivity() {

    private var _binding: ActivityInducatorTestBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityInducatorTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        testList.clear()
        testList.add("这是一个测试")
        testList.add("这是一个测试")
        testList.add("这是一个测试")
        testList.add("这是一个测试")
        testList.add("这是一个测试")
        testList.add("这是一个测试")
        testList.add("测试文案")
        testList.add("文案")

        itemWidth = (canMaxWidth / testList.size)

        binding.viewPager.adapter = ImagePagerAdapter()

        binding.indicatorView.setupWithViewPager(binding.viewPager,testList)
    }

    private val canMaxWidth = ScreenUtils.getScreenWidth() * 0.8f
    private var itemWidth = 0f

    private var testList = mutableListOf<String>()

    fun getTextWidth(name: TextView): Int {
        val spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        name.measure(spec, spec)
        return name.measuredWidth
    }

    fun getTextHeight(name: TextView): Int {
        val spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        name.measure(spec, spec)
        return name.measuredHeight
    }

    private inner class ImagePagerAdapter : PagerAdapter() {
        private val sparseArray = SparseArray<ImageView>()
        override fun getCount(): Int {
            return testList.size
        }

        override fun isViewFromObject(view: View, targetObject: Any): Boolean {
            return view === targetObject
        }

        override fun destroyItem(
            container: ViewGroup, position: Int, targetObject: Any
        ) {
            sparseArray.remove(position)
            container.removeView(targetObject as View)
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            val largeDraweeView = View.inflate(
                container.context, R.layout.include_large_drawee_view, null
            ) as ImageView

            container.addView(
                largeDraweeView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            largeDraweeView.setImageResource(R.drawable.ic_launcher_background)
            sparseArray.put(position, largeDraweeView)
            return largeDraweeView
        }

        fun getCurrentView(position: Int): ImageView {
            return sparseArray[position]
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, IndicatorTestActivity::class.java)
            context.startActivity(starter)
        }
    }
}