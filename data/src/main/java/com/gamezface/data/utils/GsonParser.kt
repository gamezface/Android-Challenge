package com.gamezface.data.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> parseObjectFromJson(response: Any?): T? {
    return Gson().fromJson(response.toString(), object : TypeToken<T>() {}.type)
}

inline fun <reified T> parseArrayFromJson(response: Any?): List<T>? {
    return Gson().fromJson(response.toString(), object : TypeToken<List<T>>() {}.type)
}
