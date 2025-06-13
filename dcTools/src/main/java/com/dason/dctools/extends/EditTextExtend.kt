package com.dason.dctools.extends

import android.content.Context
import android.text.InputFilter
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.util.regex.Pattern

/**
 * Author: KESON
 * Created on 2022/5/24 11:49
 * Desc:
 */
// 显示键盘
fun EditText.showSoftInput() {
    requestFocus()
    val manager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.showSoftInput(this, 0)
}

// 隐藏键盘
fun EditText.hideSoftInput() {
    clearFocus()
    val manager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * *@param source 将插入的字符
 * *@param start 将插入的字符开始位
 * *@param end 将插入的字符结束位
 * *@param dest 已经插入的字符
 * *@param dstart 将插入原有字符的开始位
 * *@param dend 将插入原有字符的结束位
 * *@return
 */
fun EditText.underlineFilter(length: Int) {
    val inputFilter = InputFilter { source, _, _, _, _, _ ->
        val ss = SpannableString(source)
        val spans = ss.getSpans(0, ss.length, Object::class.java)
        if (spans != null) {
            for (span in spans) {
                if (span is UnderlineSpan) {
                    return@InputFilter ""
                }
            }
        }
        null
    }
    filters = arrayOf(inputFilter, InputFilter.LengthFilter(length))
}

/**
 * 首位不能输入空格
 * @param length 最大长度
 */
fun EditText.setFirstNoBlank(length: Int) {
    val inputFilter = InputFilter { source, _, _, _, dstart, _ ->
        if (source == " " && dstart == 0) {
            return@InputFilter ""
        }
        null
    }
    filters = arrayOf(inputFilter, InputFilter.LengthFilter(length))
}

/**
 * 首位不能输入0
 * @param length 最大长度
 */
fun EditText.setFirstNoZero(length: Int) {
    val inputFilter = InputFilter { source, _, _, _, dstart, _ ->
        if (source == "0" && dstart == 0) {
            return@InputFilter ""
        }
        null
    }
    filters = arrayOf(inputFilter, InputFilter.LengthFilter(length))
}

/**
 * 小数点后几位
 * @param length 最大长度
 */
fun EditText.setPointNum(length: Int, pointNum: Int) {
    val pattern = Pattern.compile("[0-9]{0,}+(\\.[0-9]{0,})?")
    val inputFilter = InputFilter { source, _, _, dest, _, _ ->
        val dValue = dest.toString()
        if (source == ".") {
            if (dest.isEmpty()) {
                return@InputFilter "0."
            } else if (dValue.contains(".")) {
                return@InputFilter ""
            }
        }
        if (dest.isEmpty() && source == "0") {
            return@InputFilter "0."
        }
        val matcher = pattern.matcher(source.toString())
        if (!matcher.matches()) {
            return@InputFilter ""
        }
        val splitArray = dValue.split("\\.".toRegex()).toTypedArray()
        if (splitArray.size > 1) {
            val numberValue = splitArray[0]
            val dotValue = splitArray[1]
            val curInputLength = numberValue.length + dotValue.length + 1

            if (curInputLength >= length) {
                return@InputFilter ""
            }

            if (inCursorBefore() && curInputLength >= length) {
                return@InputFilter ""
            }

            if (!inCursorBefore() && dotValue.length >= pointNum) {
                return@InputFilter ""
            }
        }
        null
    }
    filters = arrayOf(inputFilter, InputFilter.LengthFilter(length))
}

/**
 * 判断「光标位置」是否在「点」之前
 */
fun EditText.inCursorBefore(): Boolean {
    val text = text.toString().trim()
    val dotPosition = text.indexOf(".")
    if (dotPosition == -1) return true
    return dotPosition >= selectionStart
}
