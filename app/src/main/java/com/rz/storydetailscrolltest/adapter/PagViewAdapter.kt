package com.rz.storydetailscrolltest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rz.storydetailscrolltest.databinding.PagItemBinding
import org.libpag.PAGFile

class PagViewAdapter(val context: Context) : RecyclerView.Adapter<PagViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagViewAdapter.ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PagViewAdapter.ViewHolder, position: Int) {
        holder.bindData(context, list, position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: PagItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(context: Context, list: MutableList<String>, position: Int) {
            val pagFile1 = PAGFile.Load(context.assets, list[position])
            binding.pagView.composition = pagFile1
            binding.pagView.setRepeatCount(0)
            binding.pagView.play()
        }

        companion object {
            fun create(parent: ViewGroup) = ViewHolder(
                PagItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    private var list: MutableList<String> = mutableListOf()

    fun setList(strList: List<String>) {
        list.clear()
        list.addAll(strList)
        notifyDataSetChanged()
    }
}