package com.rz.storydetailscrolltest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rz.storydetailscrolltest.databinding.CardItemBinding
import com.rz.storydetailscrolltest.dp
import li.etc.skycommons.os.windowSize

class CardAdapter : RecyclerView.Adapter<CardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bindView(listdata[position])
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    private var listdata: MutableList<String> = mutableListOf()

    fun setList(list: List<String>) {
        listdata.clear()
        listdata.addAll(list)
        notifyDataSetChanged()
    }
}

class CardViewHolder(val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private val maxHeight = 450f.dp()

    fun bindView(s: String) {
        //binding.root.setOnClickListener {
        //    var itemHeight = binding.root.height.toFloat()
        //    var cardHeight = binding.ivView.height.toFloat()
        //    Log.e("CardAdapter", "bindView, itemHeight: $itemHeight cardHeight: $cardHeight")
        //}
        //binding.ivView.post {
        //    var itemHeight = binding.root.height.toFloat()
        //    //val ratio = itemWidth / itemHeight
        //    val params = binding.ivView.layoutParams
        //
        //    if (itemHeight > maxHeight) {
        //        itemHeight = maxHeight
        //    }
        //
        //    params.width = (itemHeight * 0.67f).toInt()
        //    params.height = itemHeight.toInt()
        //    binding.ivView.layoutParams = params
        //}
    }

    companion object {
        fun create(parent: ViewGroup) = CardViewHolder(
            CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}