package com.rz.storydetailscrolltest.adapter

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T, VH : RecyclerView.ViewHolder?> : RecyclerView.Adapter<VH>() {

    @JvmField val list: MutableList<T> = mutableListOf()
    @JvmField val lock = Any()

    fun size(): Int {
        return list.size
    }

    operator fun get(position: Int): T? {
        return if (position < list.size) {
            list[position]
        } else null
    }

    open val isEmpty: Boolean
        get() = list.isEmpty()

    @Synchronized
    fun replace(position: Int, item: T) {
        val size = list.size
        if (position < size) {
            list[position] = item
            notifyItemChanged(position)
        }
    }

    @Synchronized
    open fun replace(collection: Collection<T>?) {
        list.clear()
        if (collection != null && !collection.isEmpty()) {
            list.addAll(collection)
        }
        notifyDataSetChanged()
    }

    @Synchronized
    open fun add(position: Int, item: T) {
        list.add(position, item)
        notifyItemInserted(position)
    }

    @Synchronized
    open fun add(item: T): Boolean {
        val lastIndex = list.size
        return if (list.add(item)) {
            notifyItemInserted(lastIndex)
            true
        } else {
            false
        }
    }

    @Synchronized
    open fun addAll(position: Int, collection: Collection<T>): Boolean {
        return if (list.addAll(position, collection)) {
            notifyItemRangeInserted(position, collection.size)
            true
        } else {
            false
        }
    }

    @Synchronized
    open fun addAll(collection: Collection<T>): Boolean {
        val lastIndex = list.size
        return if (list.addAll(collection)) {
            notifyItemRangeInserted(lastIndex, collection.size)
            true
        } else {
            false
        }
    }

    @Synchronized
    open fun clear() {
        val size = list.size
        if (size > 0) {
            list.clear()
            notifyItemRangeRemoved(0, size)
        }
    }

    @Synchronized
    open fun remove(position: Int): T? {
        if (position < 0) {
            return null
        }
        if (position >= list.size) {
            return null
        }
        val size = list.size
        val item = list.removeAt(position)
        if (size > 1) {
            notifyItemRemoved(position)
        } else {
            notifyDataSetChanged()
        }
        return item
    }

    @Synchronized
    open fun remove(item: T): Boolean {
        val size = list.size
        val index = list.indexOf(item)
        return if (list.remove(item)) {
            if (size > 1) {
                notifyItemRemoved(index)
            } else {
                notifyDataSetChanged()
            }
            true
        } else {
            false
        }
    }

    @Synchronized
    open fun removeAll(collection: Collection<T>): Boolean {
        var modified = false
        val iterator = list.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (collection.contains(item)) {
                list.indexOf(item)
                iterator.remove()
                modified = true
            }
        }
        notifyDataSetChanged()
        return modified
    }
}