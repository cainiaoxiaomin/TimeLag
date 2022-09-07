package com.example.timelagutil

/**
 * 事件bean
 *
 * @since 2022-08-07
 */
data class TimeLagItemBean(
    val actionName: String = "",
    val actionTime: Long = 0L,
    val delayTime: String = "",
    val delayTimeFromUserAction: String = "",
    val type: Int = 0
)