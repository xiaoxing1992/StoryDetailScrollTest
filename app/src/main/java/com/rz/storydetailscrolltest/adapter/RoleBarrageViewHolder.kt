package com.rz.storydetailscrolltest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rz.storydetailscrolltest.databinding.ItemRoleBarrageBinding

class RoleBarrageViewHolder(private val viewBinding: ItemRoleBarrageBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bindView(text: String) {
        viewBinding.root.text = text
    }

    companion object {
        fun create(parent: ViewGroup) = RoleBarrageViewHolder(
            ItemRoleBarrageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}