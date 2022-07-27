package com.rz.storydetailscrolltest.pga

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rz.storydetailscrolltest.adapter.PagViewAdapter
import com.rz.storydetailscrolltest.databinding.ActivityPagBinding
import org.libpag.PAGFile

class PAGTestActivity : AppCompatActivity() {

    private var _binding: ActivityPagBinding? = null
    private val binding get() = _binding!!

    private val listAdapter by lazy { PagViewAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPagBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.recyclerView.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(this@PAGTestActivity)

        }

        val mulist = mutableListOf<String>()
        for (index in 0..20) {
            mulist.add("$index.pag")
        }


        binding.btTest.setOnClickListener {
            val pagFile1 = PAGFile.Load(assets, "light.pag")
            binding.pagView.composition = pagFile1
            binding.pagView.setRepeatCount(0)
            binding.pagView.play()
            listAdapter.setList(mulist)
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, PAGTestActivity::class.java)
            context.startActivity(starter)
        }
    }
}