package com.zestworks.application.model

import android.text.TextUtils
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Collections.emptyList


class TypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<Data> {
        if (data == null) {
            return emptyList()
        }

        val listType = object : TypeToken<List<Data>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<Data>): String {
        return gson.toJson(someObjects)
    }


    @TypeConverter
    fun stringToLocation(string: String): Location? {
        if (TextUtils.isEmpty(string))
            return null
        return gson.fromJson(string, Location::class.java)
    }

    @TypeConverter
    fun locationToString(outboxItem: Location): String {
        return gson.toJson(outboxItem)
    }

}