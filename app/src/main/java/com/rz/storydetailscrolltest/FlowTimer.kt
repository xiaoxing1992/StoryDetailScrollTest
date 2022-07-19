package com.rz.storydetailscrolltest

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.yield

object FlowTimer {

    /**
     * 倒计时
     * @param totalSecond 倒计时秒数
     * */
    fun countDown(totalSecond: Long) = flow {
        for (i in totalSecond downTo 0) {
            emit(i)
            delay(1000)
            yield()
        }
    }

    /**
     * 间隔执行
     * @param initialDelay 初始延时毫秒
     * @param period 每间隔毫秒执行
     * */
    fun interval(initialDelay: Long, period: Long) = flow {
        if (initialDelay > 0) {
            delay(initialDelay)
        }
        while (true) {
            emit(Unit)
            delay(period)
            yield()
        }
    }
}