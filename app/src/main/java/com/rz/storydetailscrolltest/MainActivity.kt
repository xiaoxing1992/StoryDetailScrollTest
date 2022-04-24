package com.rz.storydetailscrolltest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.rz.storydetailscrolltest.databinding.ActivityV2Binding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityV2Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityV2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.converImageView.setOnClickListener {
            binding.sbView.bindStyle()
        }
        binding.converImageView.post {
            binding.converImageView.pivotX = binding.converImageView.width / 2f
            binding.converImageView.pivotY = binding.converImageView.height.toFloat()
        }

        binding.scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            //toolbarComponent.toggleFloatStyle(scrollY >= ((160 + 20 + 15 + 40).dp()), dominantColor)

            var scaleB = scrollY / dpToPx(560f).toFloat()

            var tranToolbar = scrollY / binding.titleView.top.toFloat()

            Log.e(
                "MainActivity", "onCreate" + "\nscaleB===" + scaleB + "\nscrollY===" + scrollY
            )

            if (scrollY >= binding.titleView.top + 50f) {
                binding.toolbarTitle.alpha = 1f
                //binding.toolbarRoot.alpha = 1f
                binding.toolbarRoot.alpha = tranToolbar * 255f
            } else {
                binding.toolbarRoot.alpha = tranToolbar
                binding.toolbarTitle.alpha = 0f
            }

            if (scrollY < binding.converImageView.height) {
                binding.converImageView.scaleX = 1 - scaleB.toFloat()
                binding.converImageView.scaleY = 1 - scaleB.toFloat()
            }

        })
    }

    private fun dpToPx(dpValue: Float): Int {//dp转换为px
        val scale = resources.displayMetrics.density;//获得当前屏幕密度
        return ((dpValue * scale + 0.5f).toInt())
    }
}