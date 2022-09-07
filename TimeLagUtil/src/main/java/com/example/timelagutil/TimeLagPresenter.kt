package com.example.timelagutil

import android.content.Context
import android.os.Message
import android.util.Log
import androidx.annotation.VisibleForTesting
import com.example.timelag.TimeLagPluginEntry

/**
 * 功能描述
 *
 * @since 2022-08-13
 */
class TimeLagPresenter(private val context: Context) : TimeLagContract.Presenter {
    @VisibleForTesting
    internal var timeLagView: TimeLagContract.View? = null

    override fun setView(view: TimeLagContract.View) {
        timeLagView = view
    }

    override fun handleMessage(msg: Message) {
        Log.i(TAG, "handle message ${msg.data.get(TimeLagPluginEntry.ACTION_NAME)}")
        val actionName = msg.data.getString(TimeLagPluginEntry.ACTION_NAME)
        val currentTime = msg.data.getLong(TimeLagPluginEntry.ACTION_TIME)
        timeLagView?.addData(
            TimeLagItemBean(
                actionName = actionName ?: "",
                actionTime = currentTime,
                type = msg.what
            )
        )
    }

    companion object {
        private const val TAG = "TimeLagPresenter"
    }
}