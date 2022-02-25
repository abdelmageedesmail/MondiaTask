package com.abdelmageed.mondiatask.data.local

import android.content.Context
import android.content.SharedPreferences

class PrefrenceStorage() {
    lateinit var sharedPref: SharedPreferences

    var key: String = "userData"
    lateinit var editor: SharedPreferences.Editor
     lateinit var context: Context

    constructor(context: Context) : this() {
        this.context = context
        sharedPref = context.getSharedPreferences(key, Context.MODE_PRIVATE)
    }

    public fun storeId(id: String) {
        editor = sharedPref.edit()
        editor.putString("id", id)
        editor.commit()
    }

    public fun getId(): String? {
        val userId = sharedPref.getString("id", "null")
        return userId
    }

    public fun storeValue(key: String, value: String) {
        editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    public fun getValue(key: String): String? {
        val value = sharedPref.getString(key, "null")
        return value
    }

    public fun clearSharedPref() {
        editor = sharedPref.edit()
        editor.clear().apply()

    }
}