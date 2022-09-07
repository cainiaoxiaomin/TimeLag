/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2022-2022. All rights reserved.
 */

package com.example.timelagutil

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * 时延列表数据适配器
 *
 * @since 2022-08-07
 */
class TimeLagRecyclerViewAdapter(private val context: Context) : RecyclerView.Adapter<TimeLagItemHolder>() {
    private var timeLagDataList = mutableListOf<TimeLagItemBean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLagItemHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.time_lag_item, parent, false)
        return TimeLagItemHolder(view)
    }

    override fun onBindViewHolder(holder: TimeLagItemHolder, position: Int) {
        holder.bindView(timeLagDataList, position)
    }

    override fun getItemCount(): Int {
        return timeLagDataList.size
    }

    /**
     * 添加数据
     *
     * @param data TimeLagItemBean 数据bean
     */
    fun addData(data: TimeLagItemBean) {
        timeLagDataList.add(data)
        this.notifyDataSetChanged()
    }
}