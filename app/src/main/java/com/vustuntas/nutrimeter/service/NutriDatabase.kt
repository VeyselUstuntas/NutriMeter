package com.vustuntas.nutrimeter.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vustuntas.nutrimeter.model.Nutrition

@Database(entities = arrayOf(Nutrition::class), version = 1)
abstract class NutriDatabase : RoomDatabase(){
    abstract fun nutriDao() : NutriDAO

    companion object{
        @Volatile private var instance : NutriDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: createDatabase(context).also {
                val database = it
                instance = database
            }
        }

        private fun createDatabase(context : Context) = Room.databaseBuilder(context.applicationContext,NutriDatabase::class.java,"NutriMeter").build()
    }

}