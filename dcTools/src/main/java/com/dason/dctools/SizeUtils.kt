package com.dason.dctools

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue

object SizeUtils {
    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return px值
     */
    @JvmStatic
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * px转dp
     *
     * @param pxValue px值
     * @return dp值
     */
    @JvmStatic
    fun px2dp(context: Context, pxValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    @JvmStatic
    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = Resources.getSystem().displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * px转sp
     *
     * @param pxValue px值
     * @return sp值
     */
    @JvmStatic
    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = Resources.getSystem().displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    @JvmStatic
    fun isPxVal(`val`: TypedValue?): Boolean {
        val complexUnit =
            TypedValue.COMPLEX_UNIT_MASK and (`val`!!.data shr TypedValue.COMPLEX_UNIT_SHIFT)
        if (`val`.type == TypedValue.TYPE_DIMENSION && complexUnit == TypedValue.COMPLEX_UNIT_PX) {
            return true
        }
        return false
    }

    /**
     * 各种单位转换
     *
     * 该方法存在于TypedValue
     *
     * @param unit    单位
     * @param value   值
     * @param metrics DisplayMetrics
     * @return 转换结果
     */
    @JvmStatic
    fun applyDimension(unit: Int, value: Float, metrics: DisplayMetrics): Float {
        return when (unit) {
            TypedValue.COMPLEX_UNIT_PX -> return value
            TypedValue.COMPLEX_UNIT_DIP -> return value * metrics.density
            TypedValue.COMPLEX_UNIT_SP -> return value * metrics.scaledDensity
            TypedValue.COMPLEX_UNIT_PT -> return value * metrics.xdpi * (1.0f / 72)
            TypedValue.COMPLEX_UNIT_IN -> return value * metrics.xdpi
            TypedValue.COMPLEX_UNIT_MM -> return value * metrics.xdpi * (1.0f / 25.4f)
            else -> 0f
        }
    }
}