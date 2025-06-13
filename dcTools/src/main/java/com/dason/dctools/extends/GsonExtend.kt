package com.dason.dctools.extends

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken

fun JsonObject.set(key: String, value: Any): JsonObject {
    if (value is MutableList<*>) {
        val array = JsonArray()
        value.forEach {
            array.add(JsonParser().parse(it.toJson()))
        }
        add(key, array)
        return this
    }
    if (value is JsonObject) {
        add(key, value)
        return this
    }
    if (value is Int) {
        addProperty(key, value)
    } else {
        addProperty(key, value.toString())
    }
    return this
}

fun Any?.toJson(): String? {
    this?.apply {
        return Gson().toJson(this)
    }
    return null
}
inline fun <reified T> String?.toBean(): T? {
    this?.apply {
        return try {
            Gson().fromJson(this, object : TypeToken<T>() {}.type)
        } catch (e: Exception) {
            null
        }
    }
    return null
}