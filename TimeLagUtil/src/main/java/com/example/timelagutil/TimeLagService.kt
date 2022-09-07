package com.example.timelagutil

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log

/**
 * 性能测试工具service
 *
 * @since 2022-08-05
 */
class TimeLagService : Service() {
    private var timeLagPresenter : TimeLagPresenter? = null

    private var timeLagView :TimeLagView? = null

    @SuppressLint("HandlerLeak")
    private val messenger = Messenger(object : Handler() {
        override fun handleMessage(msg: Message) {
            Log.i(TAG, "handleMessage")
            timeLagPresenter?.handleMessage(msg)
        }
    })

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        Log.i(TAG, "attachBaseContext")
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate")
        timeLagPresenter = TimeLagPresenter(this)
        timeLagView = TimeLagView(this)
        timeLagPresenter?.let { timeLagView?.setPresenter(it) }
        timeLagView?.let { timeLagPresenter?.setView(it) }
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.i(TAG, "onBind")
        timeLagView?.initView()
        return messenger.binder
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

    companion object {
        private const val TAG = "TimeLagService"
    }
}