package com.example.timelag

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log

/**
 * 性能测试插件入口类
 *
 * @since 2022-08-05
 */
class TimeLagPluginEntry {
    private var bindTime: Long = 0

    private var serviceMessenger: Messenger? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.i(TAG, "onServiceConnected")
            serviceMessenger = Messenger(service)
            sendMessage("启动监控", 0, bindTime)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.i(TAG, "onServiceDisconnected")
        }
    }


    /**
     * 拉起service
     *
     * @param context Context
     */
    open fun startService(context: Context) {
        try {
            val intent = Intent(context, Class.forName(TIME_LAG_ENTRY_SERVICE))
            context.startService(intent)
        } catch (e: ClassNotFoundException) {
            Log.e(TAG, "plugin is not installed")
        }
    }

    /**
     * 绑定service
     *
     * @param context Context 上下文
     */
    open fun bindService(context: Context) {
        try {
            bindTime = System.currentTimeMillis()
            val intent = Intent(context, Class.forName(TIME_LAG_ENTRY_SERVICE))
            context.bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE)
        } catch (e: ClassNotFoundException) {
            Log.e(TAG, "plugin is not installed")
        }
    }

    /**
     * 发送事件
     *
     * @param actionName String 事件名
     * @param type Int 类型，0和1,0表示用户触发事件，作为计时起点
     */
    fun sendMessage(actionName: String, type: Int) {
        sendMessage(actionName, type, CURRENT_TIME)
    }

    /**
     * 发送事件
     *
     * @param actionName String 事件名称
     * @param type Int 类型
     * @param time Long 事件时间
     */
    fun sendMessage(actionName: String, type: Int, time: Long) {
        val message = Message.obtain()
        message.what = type
        val bundle = Bundle()
        bundle.putString(ACTION_NAME, actionName)
        bundle.putLong(ACTION_TIME, if (time == CURRENT_TIME) System.currentTimeMillis() else time)
        message.data = bundle
        serviceMessenger?.send(message)
    }

    companion object {
        /**
         * 单例模式
         *
         * @return TimeLagPluginEntry 入口实现类
         */
        @JvmStatic
        fun getInstance() = InstanceHelper.sSingle

        const val ACTION_NAME = "action_name"

        const val ACTION_TIME = "action_time"

        const val TIME_LAG_START = 0

        const val TIME_LAG_ACTION = 1

        private const val TAG = "TimeLagPluginEntry"

        private const val TIME_LAG_ENTRY_SERVICE = "com.example.timelagutil.TimeLagService"

        private const val CURRENT_TIME = -1L

        private object InstanceHelper {
            val sSingle = TimeLagPluginEntry()
        }
    }
}