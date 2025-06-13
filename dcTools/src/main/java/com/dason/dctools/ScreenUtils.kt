package com.dason.dctools

import android.annotation.SuppressLint
import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources.NotFoundException
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.Display
import android.view.Surface
import android.view.WindowManager

object ScreenUtils {

    /**
     * 判断是否横屏
     *
     * @return `true`: 是 `false`: 否
     */
    @JvmStatic
    fun isLandscape(context: Context): Boolean {
        return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    /**
     * 判断是否竖屏
     *
     * @return `true`: 是`false`: 否
     */
    @JvmStatic
    fun isPortrait(context: Context): Boolean {
        return context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

    /**
     * 获取屏幕旋转角度
     *
     * @param activity activity
     * @return 屏幕旋转角度
     */
    @JvmStatic
    fun getScreenRotation(activity: Activity): Int {
        return when (activity.windowManager.defaultDisplay.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> 0
        }
    }

    /**
     * 判断是否锁屏
     *
     * @return `true`: 是`false`: 否
     */
    @JvmStatic
    fun isScreenLock(context: Context): Boolean {
        val km = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        return km.inKeyguardRestrictedInputMode()
    }

    /**
     * 获取状态栏高度
     *
     * @return 状态栏高度
     */
    @SuppressLint("InternalInsetResource")
    @JvmStatic
    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        try {
            val resourceId =
                context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = context.resources.getDimensionPixelSize(resourceId)
            }
        } catch (e: NotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    @SuppressLint("ObsoleteSdkInt")
    @JvmStatic
    fun getScreenSize(context: Context): IntArray {
        val size = IntArray(2)
        val w = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val d = w.defaultDisplay
        val metrics = DisplayMetrics()
        d.getMetrics(metrics)
        // since SDK_INT = 1;
        var widthPixels = metrics.widthPixels
        var heightPixels = metrics.heightPixels
        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT in 14..16) {
            try {
                widthPixels = Display::class.java.getMethod("getRawWidth").invoke(d) as Int
                heightPixels =
                    Display::class.java.getMethod("getRawHeight").invoke(d) as Int
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                val realSize = Point()
                Display::class.java.getMethod("getRealSize", Point::class.java).invoke(d, realSize)
                widthPixels = realSize.x
                heightPixels = realSize.y
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        size[0] = widthPixels
        size[1] = heightPixels
        return size
    }


    /**
     * 获取屏幕的宽度（单位：px）
     *
     * @return 屏幕宽
     */
    @JvmStatic
    fun getScreenWidth(context: Context): Int {
        val size = getScreenSize(context)
        return size[0]
    }

    /**
     * 获取屏幕的高度（单位：px）
     *
     * @return 屏幕高
     */
    @JvmStatic
    fun getScreenHeight(context: Context): Int {
        val size = getScreenSize(context)
        return size[1]
    }
}