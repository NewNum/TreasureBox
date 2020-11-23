package com.huxh.treasurebox.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huxh.treasurebox.R
import com.huxh.treasurebox.baselib.utils.RecyclerViewHolder
import kotlinx.android.synthetic.main.item_widget.*

class HomeAdapter(private val list: List<WidgetInfo>) : RecyclerView.Adapter<RecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder.Companion.createViewHolder(parent, R.layout.item_widget)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val widgetInfo = list[position]
        holder.tvName.text = widgetInfo.name
        holder.tvDesc.text = widgetInfo.desc
        holder.tvUrl.text = widgetInfo.url
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(widgetInfo,it,holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    var onItemClickListener: ((WidgetInfo, View, Int) -> Unit)? = null
}