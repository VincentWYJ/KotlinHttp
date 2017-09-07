package com.dylan.kotlinhttp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.dylan.kotlinhttp.R
import com.dylan.kotlinhttp.interfase.HttpTypeItemClickListener
import com.dylan.kotlinhttp.ui.*

internal class HttpTypeAdapter(private val mContext: Context,
                               private val mItemClickListener: HttpTypeItemClickListener,
                               private val mHttpTypes: Array<String>):
        RecyclerView.Adapter<HttpTypeAdapter.HttpTypeViewHolder>(), View.OnClickListener {
    override fun onClick(v: View) {
        mItemClickListener.onItemClick(v, v.tag as Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HttpTypeViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.http_type, parent, false)
        view.setOnClickListener(this@HttpTypeAdapter)
        return HttpTypeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HttpTypeViewHolder, position: Int) {
        holder.mHttpType.text = mHttpTypes[position]
        holder.itemView.tag = position
    }

    override fun getItemCount(): Int {
        return mHttpTypes.size
    }

    internal inner class HttpTypeViewHolder(private val view: View): ViewHolder(view) {
        var mHttpType: TextView = view.findViewById(R.id.http_type)
    }
}