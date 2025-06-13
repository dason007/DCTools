package com.dason.dctools

import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object KeyboardUtils {
    /**
     * 打开软键盘
     *
     * @param view
     */
    @JvmStatic
    fun showSoftInput(view: View) {
        if (EmptyUtils.isEmpty(view)) {
            return
        }
        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.visibility = View.VISIBLE
        view.requestFocus()
        val inputManager =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(view, InputMethodManager.RESULT_SHOWN)
        inputManager.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    /**
     * 关闭软键盘
     *
     * @param view t输入框
     */
    @JvmStatic
    fun hideSoftInput(view: View) {
        hideSoftInput(view, false)
    }

    @JvmStatic
    fun hideSoftInput(view: View, clearFocus: Boolean) {
        if (EmptyUtils.isEmpty(view)) {
            return
        }
        if (clearFocus) {
            view.clearFocus()
        }
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * 判断当前软键盘是否打开
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun isSoftInputShow(context: Context): Boolean {
        val inputManger =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputManger.isActive
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击Et时则不能隐藏
     */
    @JvmStatic
    fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v != null && (v is EditText)) {
            val l = intArrayOf(0, 0)
            v.getLocationOnScreen(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return !(event.rawX > left && event.rawX < right && event.rawY > top && event.rawY < bottom)
        }
        return false
    }
}