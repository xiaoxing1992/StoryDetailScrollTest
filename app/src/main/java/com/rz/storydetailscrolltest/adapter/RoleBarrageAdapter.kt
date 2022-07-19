package com.rz.storydetailscrolltest.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rz.storydetailscrolltest.R

class RoleBarrageAdapter : BaseRecyclerAdapter<String, RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int = R.layout.item_role_barrage

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RoleBarrageViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val realPosition = position % list.size

        (holder as RoleBarrageViewHolder).bindView(list[realPosition])
    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }
}