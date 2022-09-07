/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2022-2022. All rights reserved.
 */

package com.example.timelagutil

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timelagutil.databinding.TimeLagRootLayoutBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import java.util.Calendar

/**
 * 时延测试工具视图
 *
 * @since 2022-08-06
 */
class TimeLagView(private val context: Context) : TimeLagContract.View {
    private var timeLagPresenter: TimeLagContract.Presenter? = null

    private val binding = TimeLagRootLayoutBinding.inflate(LayoutInflater.from(context))

    private var refreshCurrentTimeJob: Job? = null

    private val workScope = CoroutineScope(Dispatchers.IO)

    private val uiScope = CoroutineScope(Dispatchers.Main)

    private val windowViewManager = TimeLagWindowManager(context)

    private val timeLagRecyclerViewAdapter = TimeLagRecyclerViewAdapter(context)

    override fun setPresenter(presenter: TimeLagContract.Presenter) {
        timeLagPresenter = presenter
    }

    override fun initView() {
        refreshCurrentTime()
        initCloseBtn()
        initRecyclerView()
        windowViewManager.showView(binding.root)
    }

    override fun addData(data: TimeLagItemBean) {
        timeLagRecyclerViewAdapter.addData(data)
        binding.recyclerview.layoutManager?.scrollToPosition(timeLagRecyclerViewAdapter.itemCount - 1)
    }

    private fun initRecyclerView() {
        val verticalLayoutManager = LinearLayoutManager(context)
        verticalLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerview.layoutManager = verticalLayoutManager
        binding.recyclerview.adapter = timeLagRecyclerViewAdapter
    }

    private fun initCloseBtn() {
        binding.closeBtn.setOnClickListener {
            refreshCurrentTimeJob?.cancel()
            windowViewManager.removeView(binding.root)
            (context as TimeLagService).stopSelf()
        }
    }

    private fun refreshCurrentTime() {
        refreshCurrentTimeJob = workScope.launch {
            do {
                delay(REFRESH_FREQUENCY)
                uiScope.launch {
                    val calendar = Calendar.getInstance()
                    binding.currentTime.text =
                        "当前时间：" + calendar.get(Calendar.MINUTE) +
                        ":" + calendar.get(Calendar.SECOND) +
                        ":" + calendar.get(Calendar.MILLISECOND)
                }
            } while (REFRESH_CONDITIONS)
        }
    }

    companion object {
        var REFRESH_FREQUENCY = 50L
        var REFRESH_CONDITIONS = true
    }
}