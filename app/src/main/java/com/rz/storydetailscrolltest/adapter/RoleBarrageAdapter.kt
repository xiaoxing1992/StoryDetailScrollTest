package com.rz.storydetailscrolltest.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rz.storydetailscrolltest.R

class RoleBarrageAdapter : BaseRecyclerAdapter<String, RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            R.layout.item_role_barrage_empty
        } else {
            R.layout.item_role_barrage
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_role_barrage_empty -> RoleBarrageEmptyViewHolder.create(parent)
            R.layout.item_role_barrage -> RoleBarrageViewHolder.create(parent)
            else -> RoleBarrageViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            R.layout.item_role_barrage -> {
                val realPosition = position % list.size

                (holder as RoleBarrageViewHolder).bindView(list[realPosition])
            }
        }
    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }
}