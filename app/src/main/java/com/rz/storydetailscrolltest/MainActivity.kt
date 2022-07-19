package com.rz.storydetailscrolltest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rz.storydetailscrolltest.databinding.ActivityV2Binding
import com.rz.storydetailscrolltest.pga.PAGTestActivity

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityV2Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityV2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btTest.setOnClickListener {
            IndicatorTestActivity.start(this)
            //PAGTestActivity.start(this)
        }
    }
}