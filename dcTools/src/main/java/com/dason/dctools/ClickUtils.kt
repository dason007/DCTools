package com.dason.dctools

import android.view.View

object ClickUtils {
    private var lastTime: Long = 0L

    /**
     * 针对单控件的快速点击
     */
    private fun isFastClick(v: View, delay: Long = 500): Boolean {
        val currentTime = System.currentTimeMillis()
        var lastTime = 0L
        val time = v.getTag(R.id.dc_fast_click_time)
        if (time == null) {
            v.setTag(R.id.dc_fast_click_time, currentTime)
            return false
        }
        lastTime = time as Long
        val isInvalid = currentTime - lastTime < delay
        if (!isInvalid) {
            v.setTag(R.id.dc_fast_click_time, currentTime)
        }
        return isInvalid
    }

    /**
     * 针对全局的快速点击
     */
    fun isFastClick(delay: Long = 1000): Boolean {
        val currentTime = System.currentTimeMillis()
        val isInvalid = currentTime - lastTime < delay
        if (!isInvalid) {
            lastTime = currentTime
        }
        return isInvalid
    }
}