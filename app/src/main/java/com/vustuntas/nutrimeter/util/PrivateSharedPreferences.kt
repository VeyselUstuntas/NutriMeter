package com.vustuntas.nutrimeter.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit

class PrivateSharedPreferences {
    companion object{
        private var sharedPreferences : SharedPreferences? = null

        @Volatile private var instance : PrivateSharedPreferences? = null

        private val lock = Any()
        operator fun invoke(context: Context) : PrivateSharedPreferences = instance?: synchronized(lock){
            instance?: createSharedPreferences(context).also {
                instance = it
            }
        }

        private fun createSharedPreferences(context:Context) : PrivateSharedPreferences{
            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return PrivateSharedPreferences()
        }
    }

    fun saveTime(time:Long){
        sharedPreferences?.edit(commit = true){
            putLong("TIME",time)
        }
    }

    fun getTime(): Long{
        val time = sharedPreferences?.getLong("TIME",0)
        return time?:0
    }
}