package com.example.timelagutil

import android.view.View

/**
 * windowManager接口
 *
 * @since 2022-08-06
 */
interface WindowManagerInterface {
    /**
     * 显示view
     *
     * @param view View
     */
    fun showView(view: View)

    /**
     * 移除view
     *
     * @param view View
     */
    fun removeView(view: View)
}