package com.rz.storydetailscrolltest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.rz.storydetailscrolltest.adapter.RoleBarrageAdapter
import com.rz.storydetailscrolltest.databinding.ActivityDakaBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DakaActivity : AppCompatActivity() {

    private var _binding: ActivityDakaBinding? = null
    private val binding get() = _binding!!
    private var loopJob: Job? = null

    private val roleBarrageAdapter by lazy(LazyThreadSafetyMode.NONE) {
        RoleBarrageAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDakaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val list = mutableListOf<String>().apply {
            add("1111")
            add("22")
            add("33333")
            //add("444444444444444444444")
            //add("555555")
        }

        binding.recyclerView.apply {
            setOnTouchListener { _, _ ->
                true
            }
            layoutManager = RoleBarrageLayoutManager().apply {
                horizontalCustomGap = 8.dp()
                verticalCustomGap = 0
            }
            adapter = roleBarrageAdapter
        }

        roleBarrageAdapter.replace(list)



        binding.btTest.setOnClickListener {
            binding.clView.bindView(list)
            startLoopData()
            //binding.clView.startAnimator()
        }
    }

    private fun stopLoopData() {
        loopJob?.cancel()
    }

    private fun startLoopData() {

        loopJob = lifecycleScope.launch {
            FlowTimer.interval(10, 30).catch { }.collect {
                    binding.recyclerView.smoothScrollBy(10, 0)
                }
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, DakaActivity::class.java)
            context.startActivity(starter)
        }
    }
}