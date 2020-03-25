package com.dror.mygallery.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.DisplayMetrics

class Utils {
    companion object {
        private val prefsName = "myGalleryPrefs"
        fun getDisplayMetrics(context: Activity): DisplayMetrics {
            val displayMetrics = DisplayMetrics()
            context.windowManager.defaultDisplay?.getMetrics(displayMetrics)

            return displayMetrics
        }

        fun getPreferences(context: Context, key: String, defaultValue: String? = null): String? {
            val sharedPref: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

            return sharedPref.getString(key, defaultValue)
        }

        fun getPreferences(context: Context, key: String, defaultValue: Boolean = false): Boolean {
            val sharedPref: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

            return sharedPref.getBoolean(key, defaultValue)
        }

        fun setPreferences(context: Context, key: String, value: String?) {
            val sharedPref: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPref.edit()

            editor.putString(key, value)
            editor.apply()
        }

        fun setPreferences(context: Context, key: String, value: Boolean) {
            val sharedPref: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPref.edit()

            editor.putBoolean(key, value)
            editor.apply()
        }
    }
}