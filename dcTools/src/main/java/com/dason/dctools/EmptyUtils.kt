package com.dason.dctools

import android.os.Build
import android.util.LongSparseArray
import android.util.SparseArray
import android.util.SparseBooleanArray
import android.util.SparseIntArray
import android.util.SparseLongArray
import java.lang.ref.WeakReference
import java.lang.reflect.Array

object EmptyUtils {
    /**
     * 判断对象是否为空
     *
     * @param obj 对象
     * @return `true`: 为空<br></br>`false`: 不为空
     */
    @JvmStatic
    fun isEmpty(obj: Any?): Boolean {
        if (null == obj) {
            return true
        }
        if (obj is String && obj.toString().isEmpty()) {
            return true
        }
        if (obj.javaClass.isArray && Array.getLength(obj) == 0) {
            return true
        }
        if (obj is Collection<*> && obj.isEmpty()) {
            return true
        }
        if (obj is Map<*, *> && obj.isEmpty()) {
            return true
        }
        if (obj is SparseArray<*> && obj.size() == 0) {
            return true
        }
        if (obj is SparseBooleanArray && obj.size() == 0) {
            return true
        }
        if (obj is SparseIntArray && obj.size() == 0) {
            return true
        }
        if (obj is WeakReference<*> && isEmpty(obj.get())) {
            return true
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (obj is SparseLongArray && obj.size() == 0) {
                return true
            }
        }
        if (obj is LongSparseArray<*> && obj.size() == 0) {
            return true
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (obj is LongSparseArray<*> && obj.size() == 0) {
                return true
            }
        }
        return false
    }


    @JvmStatic
    fun isNotEmpty(obj: Any?): Boolean {
        return !isEmpty(obj)
    }
}