package com.digitaldose.medtime.utils.converters

import androidx.room.TypeConverter
import java.lang.reflect.Type
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * @author Fabiano Amaral Alves <fabianoamaral445@gmail.com>
 * @since 09/03/2025
 */
class MedicamentoLocalConverter {
    @TypeConverter
    fun fromString(value: String?): List<String>? {
        if (value == null) {
            return null
        }
        val listType: Type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>?): String?
    {
        if (list == null) {
            return null
        }
        val gson = Gson()
        return gson.toJson(list)
    }
}