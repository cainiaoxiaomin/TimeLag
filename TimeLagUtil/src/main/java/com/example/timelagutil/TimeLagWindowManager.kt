/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2022-2022. All rights reserved.
 */

package com.example.timelagutil

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.View
import android.view.WindowManager

/**
 * window管理类
 *
 * @since 2022-08-06
 */
class TimeLagWindowManager(private val context: Context) : WindowManagerInterface {
    private var windowManager: WindowManager? = null

    init {
        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
    }

    override fun showView(view: View) {
        val windowParameters = WindowManager.LayoutParams()
        windowParameters.title = TAG
        windowParameters.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        windowParameters.format = PixelFormat.RGBA_8888
        windowParameters.gravity = Gravity.TOP or Gravity.LEFT
        windowParameters.width = floatViewWidth
        windowParameters.height = floatViewHeight
        windowParameters.flags =
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        windowManager?.addView(view, windowParameters)
    }

    override fun removeView(view: View) {
        windowManager?.removeViewImmediate(view)
    }

    private val floatViewHeight by lazy {
        context.resources.getDimensionPixelSize(R.dimen.time_lag_window_height)
    }

    private val floatViewWidth by lazy {
        context.resources.getDimensionPixelSize(R.dimen.time_lag_window_width)
    }

    companion object {
        private const val TAG = "TimeLagWindowManager"
    }
}