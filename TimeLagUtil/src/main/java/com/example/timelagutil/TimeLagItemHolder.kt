package com.example.timelagutil

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.RecyclerView
import com.example.timelag.TimeLagPluginEntry.Companion.TIME_LAG_START

import java.text.SimpleDateFormat

/**
 * 时延显示列表视图item
 *
 * @since 2022-08-07
 */
class TimeLagItemHolder(val view: View) : RecyclerView.ViewHolder(view) {
    @VisibleForTesting
    internal val actionName by lazy {
        view.findViewById<TextView>(R.id.action_name)
    }

    @VisibleForTesting
    internal val delayTimeFromLastAction by lazy {
        view.findViewById<TextView>(R.id.delay_time_from_last_action)
    }

    @VisibleForTesting
    internal val delayTimeFromUserAction by lazy {
        view.findViewById<TextView>(R.id.delay_time_from_user_action)
    }

    @VisibleForTesting
    internal val actionTime by lazy {
        view.findViewById<TextView>(R.id.action_time)
    }

    /**
     * 绑定view
     *
     * @param dataList MutableList<TimeLagItemBean> 数据列表
     * @param position Int 显示item位置
     */
    fun bindView(dataList: MutableList<TimeLagItemBean>, position: Int) {
        val currentAction = dataList[position]
        val lastAction = findLastAction(dataList, position)
        val lastUserAction = findLastUserAction(dataList, position)

        bindActionName(currentAction)
        bindDelayTimeFormLastAction(lastAction, currentAction)
        bindLastUserActionTime(lastUserAction, currentAction)
        bindCurrentActionTime(currentAction)
    }

    private fun bindLastUserActionTime(
        lastUserAction: TimeLagItemBean?,
        currentAction: TimeLagItemBean
    ) {
        var delayTimeFromLastUserAction = "1"
        Log.i(TAG, "${lastUserAction?.actionName}")
        lastUserAction?.let {
            delayTimeFromLastUserAction = (currentAction.actionTime - it.actionTime).toString()
        }

        delayTimeFromUserAction.text = delayTimeFromLastUserAction
    }

    private fun bindCurrentActionTime(currentAction: TimeLagItemBean) {
        val time = SimpleDateFormat("mm:ss:SSS")
        actionTime.text = time.format(currentAction.actionTime)
    }

    private fun bindDelayTimeFormLastAction(
        lastAction: TimeLagItemBean?,
        currentAction: TimeLagItemBean
    ) {
        var delayTime = "0"
        lastAction?.let {
            if (currentAction.type != TIME_LAG_START) {
                delayTime = (currentAction.actionTime - lastAction.actionTime).toString()
            }
        }
        delayTimeFromLastAction.text = delayTime
    }

    private fun bindActionName(currentAction: TimeLagItemBean) {
        if (currentAction.type == 0) {
            actionName.setTextColor(Color.RED)
        } else {
            actionName.setTextColor(Color.WHITE)
        }
        actionName.text = currentAction.actionName
    }

    private fun findLastUserAction(dataList: MutableList<TimeLagItemBean>, position: Int): TimeLagItemBean? {
        for (i in 0 until position + 1) {
            if (dataList[position - i].type == TIME_LAG_START) {
                return dataList[position - i]
            }
        }
        return null
    }

    private fun findLastAction(dataList: MutableList<TimeLagItemBean>, position: Int): TimeLagItemBean? {
        if (position - 1 >= 0) {
            return dataList[position - 1]
        }
        return null
    }

    companion object {
        private const val TAG = "TimeLagItemHolder"
    }
}