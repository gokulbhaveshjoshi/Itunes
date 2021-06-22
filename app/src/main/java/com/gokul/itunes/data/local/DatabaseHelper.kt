package com.gokul.itunes.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gokul.itunes.song.domain.model.Result


@Database(entities = arrayOf(Result::class), version = DatabaseHelper.DATABASE_VERSION)
abstract class DatabaseHelper: RoomDatabase() {

    abstract fun songDao(): SongDao

    companion object {
        const val DATABASE_VERSION = 1
        val DATABASE_NAME =  "songsDB"

        @Volatile
        private var INSTANCE: DatabaseHelper? = null

        fun getInstance(context: Context): DatabaseHelper {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseHelper::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }

}