package com.dason.dctools.extends

import com.tencent.mmkv.MMKV

fun Int.setToMMKV(key: String) {
    MMKV.defaultMMKV()?.putInt(key, this)
}

fun String.setToMMKV(key: String) {
    MMKV.defaultMMKV()?.putString(key, this)
}

fun Float.setToMMKV(key: String) {
    MMKV.defaultMMKV()?.putFloat(key, this)
}

fun Long.setToMMKV(key: String) {
    MMKV.defaultMMKV()?.putLong(key, this)
}

fun Boolean.setToMMKV(key: String) {
    MMKV.defaultMMKV()?.putBoolean(key, this)
}

fun ByteArray.setToMMKV(key: String) {
    MMKV.defaultMMKV()?.putBytes(key, this)
}

fun Any.setToMMKV(key: String) {
    MMKV.defaultMMKV()?.putString(key, this.toJson())
}

fun Any.removeFromMMKV(key: String) {
    MMKV.defaultMMKV()?.removeValueForKey(key)
}

inline fun <reified T> Any?.getFromMMKV(key: String): T? {
    return when (T::class.simpleName) {
        Int::class.simpleName -> {
            MMKV.defaultMMKV()?.getInt(key, 0) as? T
        }
        String::class.simpleName -> {
            MMKV.defaultMMKV()?.getString(key, null) as? T
        }
        Float::class.simpleName -> {
            MMKV.defaultMMKV()?.getFloat(key, 0f) as? T
        }
        Long::class.simpleName -> {
            MMKV.defaultMMKV()?.getLong(key, 0L) as? T
        }
        Boolean::class.simpleName -> {
            MMKV.defaultMMKV()?.getBoolean(key, false) as? T
        }
        ByteArray::class.simpleName -> {
            MMKV.defaultMMKV()?.getBytes(key, null) as? T
        }
        else -> {
            MMKV.defaultMMKV()?.getString(key, null).toBean() as? T
        }
    }
}