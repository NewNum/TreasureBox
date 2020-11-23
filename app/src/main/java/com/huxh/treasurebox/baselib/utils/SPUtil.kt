package com.huxh.treasurebox.baselib.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * @author zongjin
 * @date 2019/3/27.
 */
class SPUtil(private val context: Context) {
    private val sp: SharedPreferences =
        context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE)


    fun putInt(key: String, i: Int) {
        sp.edit().putInt(key, i).apply()
    }

    fun getInt(key: String, defaultInt: Int = 0): Int {
        return sp.getInt(key, defaultInt)
    }


    fun putLong(key: String, i: Long) {
        sp.edit().putLong(key, i).apply()
    }

    fun getLong(key: String, defaultInt: Long = 0): Long {
        return sp.getLong(key, defaultInt)
    }

    fun putString(key: String, value: String?) {
        sp.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return sp.getString(key, defaultValue)?:defaultValue
    }
}
