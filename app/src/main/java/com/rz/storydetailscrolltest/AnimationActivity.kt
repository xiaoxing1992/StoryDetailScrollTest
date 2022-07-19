package com.rz.storydetailscrolltest

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rz.storydetailscrolltest.databinding.ActivityGiftBinding

class AnimationActivity : AppCompatActivity() {

    private var _binding: ActivityGiftBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGiftBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btTest.setOnClickListener {
            buildViews(binding.giftView)
        }
    }

    //private fun buildViews(likeView: View) {
    //    val a = ObjectAnimator.ofFloat(
    //        0f,
    //        1.2f,
    //        0.9f,
    //        1.14f,
    //        1f,
    //        1f,
    //        1f,
    //        0f
    //    )
    //    a.interpolator = CustomInterpolator()
    //    a.duration = 1500L
    //    a.addUpdateListener {
    //        val currentValue = it.animatedValue
    //        likeView.scaleX = currentValue as Float
    //        likeView.scaleY = currentValue as Float
    //    }
    //    a.start()
    //}
    private fun buildViews(likeView: View) {
        val a = ObjectAnimator.ofFloat(
            0f,
            120f
        )
        a.setEvaluator(CustomEvaluator())
        //a.interpolator = CustomInterpolator()
        a.duration = 1500L
        a.addUpdateListener {
            val currentValue = it.animatedValue
            likeView.scaleX = currentValue as Float
            likeView.scaleY = currentValue as Float
        }
        a.start()
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, AnimationActivity::class.java)
            context.startActivity(starter)
        }
    }
}