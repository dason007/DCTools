package com.dason.dctools.brightness

import android.content.Context
import android.database.ContentObserver
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import com.dason.dctools.EmptyUtils
import java.lang.ref.WeakReference

class BrightnessManager(private val ct: Context) {
    private var context: Context = ct.applicationContext
    private var listener: WeakReference<IBrightnessListener>? = null

    private val brightnessObserver: ContentObserver = object : ContentObserver(null) {
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            if (EmptyUtils.isNotEmpty(listener)) {
                listener!!.get()!!.onBrightnessChanged()
            }
        }
    }

    private val brightnessModeObserver: ContentObserver = object : ContentObserver(null) {
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            if (EmptyUtils.isNotEmpty(listener)) {
                listener!!.get()!!.onBrightnessModeChanged()
            }
        }
    }



    init {
        this.registerBrightnessObserver()
    }

    /**
     * 释放资源并清理观察者
     *
     * 此函数主要用于释放与当前对象相关的资源，包括解注册亮度观察者和清除监听器
     * 它通过将监听器设置为null来解除与监听器的关联，并调用解注册亮度观察者的方法
     * 以确保不再接收不必要的回调，从而避免内存泄漏
     */
    fun release() {
        // 将监听器设置为null，解除与监听器的关联
        this.listener = null
        // 调用解注册亮度观察者的方法，避免接收不必要的回调
        this.unregisterBrightnessObserver()
    }


    /**
     * 设置亮度变化监听器
     * 通过使用弱引用来避免内存泄漏问题
     * 当IBrightnessListener的实现者（如Activity或Fragment）被销毁时，
     * 它可以被垃圾回收，而不会因为此处的引用而继续保留在内存中
     *
     * @param listener 实现了IBrightnessListener接口的实例，用于监听亮度变化
     */
    fun changeListener(listener: IBrightnessListener) {
        this.listener = WeakReference(listener)
    }


    /**
     * 获得当前屏幕亮度值
     *
     * @return0--255
     */
    fun getBrightness(): Int {
        var brightness = -1
        try {
            brightness =
                Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS)
        } catch (e: SettingNotFoundException) {
            e.printStackTrace()
        }
        return brightness
    }

    /**
     * 保存当前的屏幕亮度值，并使之生效
     *
     * @paramparamInt0-255
     */
    fun setBrightness(brightness: Int) {
        try {
            Settings.System.putInt(
                context.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
                brightness
            )
            val uri = Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS)
            context.contentResolver.notifyChange(uri, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 获得当前屏幕亮度的模式
     *
     * @return 1为自动调节屏幕亮度,0为手动调节屏幕亮度,-1获取失败
     */
    fun getBrightnessMode(): Int {
        var mode = -1
        try {
            mode = Settings.System.getInt(
                context.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE
            )
        } catch (e: SettingNotFoundException) {
            e.printStackTrace()
        }
        return mode
    }

    /**
     * 设置当前屏幕亮度的模式
     *
     * @param mode 1 为自动调节屏幕亮度,0为手动调节屏幕亮度
     */
    fun setBrightnessMode(mode: Int) {
        try {
            Settings.System.putInt(
                context.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                mode
            )
            val uri = Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS_MODE)
            context.contentResolver.notifyChange(uri, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun registerBrightnessObserver() {
        val brightness = Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS)
        val brightnessModel = Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS_MODE)
        context.contentResolver.registerContentObserver(brightness, false, brightnessObserver)
        context.contentResolver.registerContentObserver(
            brightnessModel,
            false,
            brightnessModeObserver
        )
    }

    private fun unregisterBrightnessObserver() {
        context.contentResolver.unregisterContentObserver(brightnessObserver)
        context.contentResolver.unregisterContentObserver(brightnessModeObserver)
    }


}