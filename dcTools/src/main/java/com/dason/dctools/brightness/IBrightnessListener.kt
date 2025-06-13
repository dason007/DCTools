package com.dason.dctools.brightness

interface IBrightnessListener {
    /*亮度发生变化*/
    fun onBrightnessChanged()
    /*亮度模式发生变化*/
    fun onBrightnessModeChanged()
}