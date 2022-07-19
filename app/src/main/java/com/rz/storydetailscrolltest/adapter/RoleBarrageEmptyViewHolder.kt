package com.rz.storydetailscrolltest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rz.storydetailscrolltest.databinding.ItemRoleBarrageEmptyBinding

class RoleBarrageEmptyViewHolder(viewBinding: ItemRoleBarrageEmptyBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    companion object {
        fun create(parent: ViewGroup) = RoleBarrageEmptyViewHolder(
            ItemRoleBarrageEmptyBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}