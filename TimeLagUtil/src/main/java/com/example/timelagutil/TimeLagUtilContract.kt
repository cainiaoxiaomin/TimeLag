package com.example.timelagutil

import android.os.Message

/**
 * 性能测试工具契约类
 *
 * @since 2022-08-09
 */
interface TimeLagContract {
    interface Presenter {
        /**
         * 设置view
         *
         * @param view View 视图层
         */
        fun setView(view: View)

        /**
         * 处理信息
         *
         * @param msg Message 信息，包含事件名称和时间
         */
        fun handleMessage(msg: Message)
    }

    interface View {
        /**
         * 设置代理
         *
         * @param presenter Presenter 代理
         */
        fun setPresenter(presenter: Presenter)

        /**
         * 初始化
         */
        fun initView()

        /**
         * 添加列表数据
         *
         * @param data TimeLagItemBean 数据bean
         */
        fun addData(data: TimeLagItemBean)
    }
}
