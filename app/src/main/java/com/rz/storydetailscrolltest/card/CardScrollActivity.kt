package com.rz.storydetailscrolltest.card

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.rz.storydetailscrolltest.adapter.CardAdapter
import com.rz.storydetailscrolltest.databinding.ActivityCardTestBinding
import com.rz.storydetailscrolltest.databinding.ViewstubRoleCardBinding
import com.rz.storydetailscrolltest.dp
import com.rz.storydetailscrolltest.interpolator.EaseCubicInterpolator
import li.etc.skycommons.os.windowSize
import org.libpag.PAGFile
import org.libpag.PAGView
import kotlin.math.roundToInt

class CardScrollActivity : AppCompatActivity() {

    private var _binding: ActivityCardTestBinding? = null
    private val binding get() = _binding!!
    private var testList = mutableListOf<String>()

    private val pagerSnapHelper by lazy { PagerSnapHelper() }
    private val mLayoutManager by lazy {
        LinearLayoutManager(
            this@CardScrollActivity, RecyclerView.HORIZONTAL, false
        )
    }
    private val cardAdapter: CardAdapter by lazy {
        CardAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCardTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        testList.clear()
        testList.add("这是一个测试")
        testList.add("这")
        testList.add("这是一个")
        testList.add("这是一个测试")
        testList.add("这是")
        testList.add("这是一个测试")
        testList.add("这是一")
        testList.add("这是一个测")

        initViews()
        binding.roleCardViewstub.isVisible = true
    }

    private var itemWidth = 0

    private fun initViews() {
        binding.roleCardViewstub.setOnInflateListener { stub, inflated ->
            initRoleCardView(inflated)
        }
    }

    private fun initRoleCardView(view: View) {
        val roleCardBinding = ViewstubRoleCardBinding.bind(view)




        roleCardBinding.recyclerView.apply {
            itemWidth = context.windowSize().width()
            layoutManager = mLayoutManager
            adapter = cardAdapter
            (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
            pagerSnapHelper.attachToRecyclerView(this)
        }
        roleCardBinding.hIndicator.bindRecyclerView(roleCardBinding.recyclerView)
        //binding.indicatorView.setWithRecyclerView(binding.recyclerView, RecyclerView.HORIZONTAL)
        roleCardBinding.hIndicator.setMoveCenterXListener { fl ->
            val position = getScrollPosition(roleCardBinding)
            Log.e(
                "CardScrollActivity", "setMoveListener, position:$position"
            )

            val t3ext = testList.get(position)
            roleCardBinding.testView.text = t3ext
            //val textWidth = getTextWidth(binding.testView)
            roleCardBinding.testView.translationX = roleCardBinding.hIndicator.left + fl - roleCardBinding.testView.measuredWidth / 2f
        }
        cardAdapter.setList(testList)

        //TODO  在这拿不到top的距离

        val recyclerTop = roleCardBinding.recyclerView.top
        val screenWidth = windowSize().width()
        val recyclerHeight = 300f.dp()

        val cardViewWidth = recyclerHeight * (2f / 3f)

        val svgHeight = recyclerHeight * 1.5f
        val svgWidth = cardViewWidth * 1.5f

        binding.animationLayout.updateLayoutParams<ViewGroup.LayoutParams> {
            width = svgWidth.toInt()
            height = svgHeight.toInt()
        }


        binding.cardView.updateLayoutParams<ViewGroup.LayoutParams> {
            width = cardViewWidth.toInt()
            height = recyclerHeight.toInt()
        }

        //val x =recyclerWidth/ 2f-svgWidth/2f
        val y = recyclerHeight / 2f - svgHeight / 2f +  88.dp()

        Log.e(
            "CardScrollActivity",
            "recyclerTop=>${recyclerTop}  recyclerWidth=>${screenWidth} recyclerHeight=>${recyclerHeight}" + " y=>${y} svgHeight=>${svgHeight} svgWidth=>${svgWidth}"
        )
        //if(svgWidth<=recyclerWidth){
        //    binding.animationLayout.x = x
        //}
        val centerx = screenWidth / 2f

        val animationX = centerx - svgWidth / 2

        //binding.animationLayout.x = animationX

        //binding.animationLayout.translationX = (svgWidth - screenWidth) / 2f

        Log.e("CardScrollActivity", "animationX=>$animationX viewWidth=>$cardViewWidth")
        binding.animationLayout.y = y

        binding.btLight.setOnClickListener {
            roleCardBinding.recyclerView.visibility = View.INVISIBLE
            val rotateAnimation = createRotateAnimator(binding.cardView, binding.pagView) {
                roleCardBinding.recyclerView.visibility = View.VISIBLE
            }
            rotateAnimation.start()

        }
    }

    private fun createRotateAnimator(
        cardView: View,
        svgView: PAGView,
        doEnd: () -> Unit
    ): AnimatorSet {
        val pagFile1 = PAGFile.Load(assets, "final_light.pag")
        val cardWidth = cardView.width
        //设置焦距
        cardView.cameraDistance = cardWidth * 5f * cardView.resources.displayMetrics.density
        //svgView.setCameraDistance(3000 * svgView.getResources().getDisplayMetrics().density);

        val cardRotationAnimator = ObjectAnimator.ofFloat(cardView, View.ROTATION_Y, 0f, -360f)
            .setDuration(rotateDuration)

        cardRotationAnimator.interpolator = EaseCubicInterpolator(.75f, .05f, .21f, .82f)
        val cardScaleXAnimator = ObjectAnimator.ofFloat(cardView, View.SCALE_X, 1f, 1.06f, 1f)
            .setDuration(scaleDuration).also { it.startDelay = rotateDuration }
        cardScaleXAnimator.interpolator = DecelerateInterpolator(3F)
        val cardScaleYAnimator = ObjectAnimator.ofFloat(cardView, View.SCALE_Y, 1f, 1.06f, 1f)
            .setDuration(scaleDuration).also { it.startDelay = rotateDuration }
        cardScaleYAnimator.interpolator = DecelerateInterpolator(3F)
        val svgAnimation = ObjectAnimator.ofFloat(svgView, View.ALPHA, 1f, 1f).setDuration(
            scaleDuration
        ).also { it.startDelay = 1160L }
        svgAnimation.doOnStart {
            svgView.composition = pagFile1
            svgView.play()

        }
        svgAnimation.doOnEnd {
            doEnd.invoke()
        }

        return AnimatorSet().apply {
            playTogether(cardRotationAnimator, cardScaleXAnimator, cardScaleYAnimator, svgAnimation)
        }
    }

    //private fun createScaleAnimator(view: View): AnimatorSet {
    //    val b = ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 1.06f)
    //    val c = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 1.06f)
    //
    //    return AnimatorSet().apply {
    //        playTogether(b, c)
    //        interpolator = LinearInterpolator()
    //        duration = scaleDuration
    //    }
    //}

    private fun getScrollPosition(bindview: ViewstubRoleCardBinding): Int {
        val range = bindview.recyclerView.computeHorizontalScrollOffset() / itemWidth.toFloat()
        Log.e(
            "CardScrollActivity", "getScrollPosition, range:$range"
        )

        val position = range.roundToInt()
        Log.e(
            "CardScrollActivity", "getScrollPosition, position:$position"
        )

        return position
    }

    //fun getTextWidth(name: TextView): Int {
    //    val spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    //    name.measure(spec, spec)
    //    return name.measuredWidth
    //}

    companion object {

        // 必须双数！
        private const val rotateDuration = 1320L
        private const val scaleDuration = 1800L

        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, CardScrollActivity::class.java)
            context.startActivity(starter)
        }
    }
}