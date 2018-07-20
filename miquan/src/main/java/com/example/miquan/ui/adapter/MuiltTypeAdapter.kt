package com.example.miquan.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlin.reflect.KClass

class MuiltTypeAdapter(val data: ArrayList<Any>) : RecyclerView.Adapter<BaseViewHolder>() {
    val holderMap = HashMap<Int, KClass<BaseViewHolder>>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val holder =(data.get(viewType) as ListItem<*>).viewHolder
        return holder!!
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data.get(position)as ListItem<Any> )
    }

    override fun getItemCount(): Int = data.size
    override fun getItemViewType(position: Int): Int {
        return position
    }

}

class ListItem<out T>(val viewHolder: BaseViewHolder, val data: T) {
}